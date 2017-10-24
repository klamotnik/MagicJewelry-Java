package SEngine.Components;

import SEngine.Components.Animations.BrickAnimation;
import SEngine.Components.Animations.EndGameAnimation;
import SEngine.Types.Actor;
import SEngine.Types.ImagePanel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Board extends Actor {
    public class RemoveBricksInfo {
        public RemoveBricksInfo(int removedBricks, int combo) {
            this.removedBricks = removedBricks;
            this.combo = combo;
        }

        public int removedBricks;
        public int combo;
    }

    public enum BoardColor {
        Orange(0),
        Gray(1),
        Violet(2),
        Aqua(3),
        Pink(4),
        Green(5),
        Yellow(6),
        Blue(7);

        private int nr;

        BoardColor(int nr) {
            this.nr = nr;
        }

        public int value() {
            return nr;
        }

        public static BoardColor fromValue(int nr) {
            try {
                return values()[nr];
            } catch (ArrayIndexOutOfBoundsException e) {
                return Orange;
            }
        }
    }

    public enum MoveDirection {Left, Right, Down}

    private static ImagePanel[] sprites = new ImagePanel[]{
            new ImagePanel("board/board0.png"),
            new ImagePanel("board/board1.png"),
            new ImagePanel("board/board2.png"),
            new ImagePanel("board/board3.png"),
            new ImagePanel("board/board4.png"),
            new ImagePanel("board/board5.png"),
            new ImagePanel("board/board6.png"),
            new ImagePanel("board/board7.png"),
    };
    public static final int BOARD_OFFSET_X = 10;
    public static final int BOARD_OFFSET_Y = 10;
    public static final int BRICKS_IN_ROW = 7;
    public static final int BRICKS_IN_COL = 15;

    private int fallingSpeed;
    private int timeFromLastFalling;
    private Brick[][] bricks;
    private Brick[] activeBricks;
    private boolean needBricks;
    private int bricksToRemove;
    private BrickAnimation brickAnimation;
    private int combo;
    private boolean canDeliverRemoveBricksInfo;
    private boolean endGame;
    private EndGameAnimation endGameAnimation;

    public Board(int x, int y) {
        super(x, y);
        bricks = new Brick[7][17];
        setSize(230, 469);
        add(sprites[0]);
        needBricks = true;
        endGame = false;
        combo = 0;
        canDeliverRemoveBricksInfo = false;
    }

    @Override
    public void tick(int deltaTime) {
        if (endGame) {
            if (endGameAnimation == null) {
                endGameAnimation = new EndGameAnimation(this);
                endGameAnimation.start();
            } else {
                endGameAnimation.tick(deltaTime);
                repaint();
            }
        } else if (brickAnimation != null) {
            brickAnimation.tick(deltaTime);
            if (brickAnimation.isCompleted()) {
                brickAnimation = null;
                removeBricks();
                bricksAction();
            } else
                repaint();
        } else {
            timeFromLastFalling += deltaTime;
            if (timeFromLastFalling >= fallingSpeed)
                moveBricks(MoveDirection.Down);
        }

        super.tick(deltaTime);
    }

    public RemoveBricksInfo getRemoveBricksInfo() {
        RemoveBricksInfo info = new RemoveBricksInfo(canDeliverRemoveBricksInfo ? countBricksToRemove() : 0, canDeliverRemoveBricksInfo ? combo : 0);
        canDeliverRemoveBricksInfo = false;
        return info;
    }

    private void removeBricks() {
        for (int x = 0; x < BRICKS_IN_ROW; x++)
            for (int y = 0; y < BRICKS_IN_COL; y++)
                if (bricks[x][y] != null && bricks[x][y].toRemove) {
                    remove(bricks[x][y]);
                    bricks[x][y] = null;
                }
        fallBricks();
        repaint();
    }

    private void fallBricks() {
        for (int x = 0; x < BRICKS_IN_ROW; x++) {
            int gap = 0;
            for (int y = 0; y < BRICKS_IN_COL; y++) {
                if (bricks[x][y] == null)
                    gap++;
                else if (gap > 0) {
                    bricks[x][y].setBrickPosition(x, y - gap);
                    bricks[x][y - gap] = bricks[x][y];
                    bricks[x][y] = null;
                }
            }
        }
    }

    public void swapBricks() {
        int tmpX = getBrickX(activeBricks[0].getX());
        int tmpY = getBrickY(activeBricks[0].getY());
        activeBricks[0].setBrickPosition(getBrickX(activeBricks[2].getX()), getBrickY(activeBricks[2].getY()));
        activeBricks[2].setBrickPosition(getBrickX(activeBricks[1].getX()), getBrickY(activeBricks[1].getY()));
        activeBricks[1].setBrickPosition(tmpX, tmpY);
        Brick tmp = activeBricks[0];
        activeBricks[0] = activeBricks[1];
        activeBricks[1] = activeBricks[2];
        activeBricks[2] = tmp;
        repaint();
    }

    public void moveBricks(MoveDirection direction) {
        if (direction == MoveDirection.Down)
            timeFromLastFalling = 0;
        if (canMove(direction)) {
            int activeBricksX = getBrickX(activeBricks[0].getX());
            for (int i = 0; i < 3; i++)
                activeBricks[i].setBrickPosition(
                        activeBricksX + (direction == MoveDirection.Left ? -1 : direction == MoveDirection.Right ? 1 : 0),
                        getBrickY(activeBricks[i].getY()) - (direction == MoveDirection.Down ? 1 : 0));
            repaint();
        } else if (direction == MoveDirection.Down)
            bricksAction();
    }

    private boolean canMove(MoveDirection direction) {
        int activeBricksX = getBrickX(activeBricks[0].getX());
        int activeBricksY = getBrickY(activeBricks[0].getY());
        switch (direction) {
            case Left:
                return activeBricksX - 1 >= 0 && bricks[activeBricksX - 1][activeBricksY] == null;
            case Right:
                return activeBricksX + 1 <= 6 && bricks[activeBricksX + 1][activeBricksY] == null;
            case Down:
                return activeBricksY - 1 >= 0 && bricks[activeBricksX][activeBricksY - 1] == null;
        }
        return false;
    }

    private void bricksAction() {
        boolean special = activeBricks != null && activeBricks[0].getColor() == Brick.BrickColor.Special;
        applyBricks();
        if (isBoardOverflowed()) {
            endGame = true;
            return;
        }
        if ((bricksToRemove = markBricksToRemove(special)) == 0) {
            needBricks = true;
            combo = 0;
            canDeliverRemoveBricksInfo = false;
        } else {
            needBricks = false;
            combo++;
            canDeliverRemoveBricksInfo = true;
            brickAnimation = new BrickAnimation(getBricksToRemove());
            brickAnimation.start();
        }
    }

    public boolean isBoardOverflowed() {
        if (bricks[3][BRICKS_IN_COL - 1] != null)
            return true;
        for (int i = 0; i < BRICKS_IN_ROW; ++i)
            if (bricks[i][BRICKS_IN_COL] != null)
                return true;
        return false;
    }

    private Collection<Brick> getBricksToRemove() {
        List<Brick> bricksToRemove = new ArrayList<>(this.bricksToRemove);
        for (int x = 0; x < BRICKS_IN_ROW; x++)
            for (int y = 0; y < BRICKS_IN_COL; y++)
                if (bricks[x][y] != null && bricks[x][y].toRemove)
                    bricksToRemove.add(bricks[x][y]);
        return bricksToRemove;
    }

    private int countBricksToRemove() {
        int bricksToRemove = 0;
        for (int i = 0; i < BRICKS_IN_ROW; i++)
            for (int j = 0; j < BRICKS_IN_COL; j++)
                if (bricks[i][j] != null && bricks[i][j].toRemove)
                    bricksToRemove++;
        return bricksToRemove;
    }

    public Brick.BrickColor getDemandColor() {
        for (int i = 0; i < BRICKS_IN_ROW; i++)
            for (int j = 0; j < BRICKS_IN_COL; j++)
                if (bricks[i][j] != null && bricks[i][j].getColor() == Brick.BrickColor.Special) {
                    if (j == 0)
                        return Brick.BrickColor.Special;
                    return bricks[i][j - 1].getColor();
                }
        return Brick.BrickColor.Special;
    }

    private int markBricksToRemove(boolean special) {
        if (special) {
            Brick.BrickColor demandColor = getDemandColor();
            if (demandColor == Brick.BrickColor.Special)
                return 3;
            for (int i = 0; i < BRICKS_IN_ROW; i++)
                for (int j = 0; j < BRICKS_IN_COL; j++)
                    if (bricks[i][j] != null && bricks[i][j].getColor() == demandColor)
                        bricks[i][j].toRemove = true;
        } else {
            for (int i = 0; i < BRICKS_IN_ROW; i++) {
                for (int j = 0; j < BRICKS_IN_COL; j++) {
                    if (bricks[i][j] == null)
                        continue;
                    Brick.BrickColor color = bricks[i][j].getColor();
                    if (i < BRICKS_IN_ROW - 2 && bricks[i + 1][j] != null && bricks[i + 1][j].getColor() == color && bricks[i + 2][j] != null && bricks[i + 2][j].getColor() == color)
                        for (int k = 0; k < 3; k++)
                            bricks[i + k][j].toRemove = true;
                    if (j < BRICKS_IN_COL - 2 && bricks[i][j + 1] != null && bricks[i][j + 1].getColor() == color && bricks[i][j + 2] != null && bricks[i][j + 2].getColor() == color)
                        for (int k = 0; k < 3; k++)
                            bricks[i][j + k].toRemove = true;
                    if (i < BRICKS_IN_ROW - 2 && j < BRICKS_IN_COL - 2 && bricks[i + 1][j + 1] != null && bricks[i + 1][j + 1].getColor() == color && bricks[i + 2][j + 2] != null && bricks[i + 2][j + 2].getColor() == color)
                        for (int k = 0; k < 3; k++)
                            bricks[i + k][j + k].toRemove = true;
                    if (i >= 2 && j < BRICKS_IN_COL - 2 && bricks[i - 1][j + 1] != null && bricks[i - 1][j + 1].getColor() == color && bricks[i - 2][j + 2] != null && bricks[i - 2][j + 2].getColor() == color)
                        for (int k = 0; k < 3; k++)
                            bricks[i - k][j + k].toRemove = true;
                }
            }
        }
        return countBricksToRemove();
    }

    private void applyBricks() {
        if (activeBricks == null)
            return;
        int activeBricksX = getBrickX(activeBricks[0].getX());
        int activeBricksY = getBrickY(activeBricks[0].getY());
        for (int i = 0; i < 3; i++)
            bricks[activeBricksX][activeBricksY + i] = activeBricks[i];
        activeBricks = null;
    }

    public void changeColor(BoardColor color) {
        remove(getComponentCount() - 1);
        add(sprites[color.value()], getComponentCount());
        repaint();
    }

    public void setFallingSpeed(int milliseconds) {
        fallingSpeed = milliseconds;
    }

    public boolean needBricks() {
        return needBricks;
    }

    public boolean pendingAnyAnimation() {
        return (brickAnimation != null && brickAnimation.isPending()) || (endGameAnimation != null && endGameAnimation.isPending());
    }

    public boolean isGameEnded() {
        return endGame;
    }

    public void obtainBricks(Brick[] bricks) {
        activeBricks = bricks;

        for (int i = 0; i < 3; ++i) {
            bricks[i].setBrickPosition(3, 14 + i);
            add(bricks[i], 0);
        }
        needBricks = false;
        repaint();
    }

    public int getBrickX(int x) {
        return Math.round((x - BOARD_OFFSET_X) / Brick.BRICK_WIDTH);
    }

    public int getBrickY(int y) {
        return 14 - Math.round((y - BOARD_OFFSET_Y) / Brick.BRICK_HEIGHT);
    }
}
