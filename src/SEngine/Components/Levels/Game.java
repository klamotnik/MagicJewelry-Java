package SEngine.Components.Levels;

import SEngine.Components.*;
import SEngine.LevelManager;
import SEngine.Types.Level;
import SEngine.Utils.FileHelper;

import java.awt.event.KeyEvent;

public class Game extends Level {
    private Logo logo;
    private NextBrickGroup nextBrickGroup;
    private Board board;
    private InfoPanel infoPanel;
    private static final int BRICKS_PER_LEVEL = 70;
    private boolean pause;
    private boolean nextLevel;
    private boolean inARow;
    private int inARowCounter;
    private boolean isScoreSaved;

    public Game() {
        super();
        add((logo = new Logo(310, 50, false)));
        add((nextBrickGroup = new NextBrickGroup(420, 315)));
        add((board = new Board(5, 5)));
        add((infoPanel = new InfoPanel(350, 180)));
        setBoardFallingSpeed(1);
    }

    @Override
    public void tick(int deltaTime) {
        if (pause)
            return;

        if (board.isBoardOverflowed())
            performBoardOverflowAction();

        if (board.needBricks())
            performNeedBricksAction();

        Board.RemoveBricksInfo info = board.getRemoveBricksInfo();
        if (info.removedBricks > 0)
            updateGameState(info);

        super.tick(deltaTime);
    }

    private void performBoardOverflowAction() {
        infoPanel.setGameOverMsg();
        nextBrickGroup.hide();
        if (!isScoreSaved) {
            FileHelper.saveScoreToFile(infoPanel.getScore(), infoPanel.getBricks());
            isScoreSaved = true;
        }
    }

    private void performNeedBricksAction() {
        if (!inARow) {
            inARowCounter -= 3;
            if (inARowCounter < 0)
                inARowCounter = 0;
        } else
            inARow = false;
        if (nextLevel) {
            nextLevel = false;
            infoPanel.addLevel();
            setBoardFallingSpeed(infoPanel.getLevel());
            board.changeColor(Board.BoardColor.fromValue((infoPanel.getLevel()) % 8));
            logo.changeColor(Logo.LogoColor.fromValue(infoPanel.getLevel() % 8 + 1));
        }
        if (shouldUseSpecialBricks()) {
            board.obtainBricks(new Brick[]{
                new Brick(Brick.BrickColor.Special),
                new Brick(Brick.BrickColor.Special),
                new Brick(Brick.BrickColor.Special)});
            nextLevel = true;
        } else {
            board.obtainBricks(nextBrickGroup.drawNextGroup());
        }
    }

    private void updateGameState(Board.RemoveBricksInfo info) {
        infoPanel.addBricks(info.removedBricks);
        infoPanel.addPoints(computePoints(info.removedBricks, info.combo));
        inARow = true;
        if (inARowCounter < 10)
            inARowCounter++;
    }

    private boolean shouldUseSpecialBricks() {
        return infoPanel.getBricks() >= infoPanel.getLevel() * BRICKS_PER_LEVEL;
    }

    private void setBoardFallingSpeed(int level) {
        board.setFallingSpeed((int) (level <= 10 ? (10000 / (level + 10)) * 2 : 10000 / (level * 1.25)));
    }

    private int computePoints(int bricks, int combo) {
        int val = (300 + (200 + (50 * (bricks - 4))) * (bricks - 3)) * combo;
        val += val / 10 * inARowCounter;
        return val;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (board.pendingAnyAnimation())
            return;
        if (board.isGameEnded()) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                LevelManager.getInstance().changeLevel(MainMenu.class);
        }
        if (pause) {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
                pause = false;
                infoPanel.togglePauseMsg();
            }
        } else if (!board.needBricks()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    board.moveBricks(Board.MoveDirection.Left);
                    break;
                case KeyEvent.VK_RIGHT:
                    board.moveBricks(Board.MoveDirection.Right);
                    break;
                case KeyEvent.VK_DOWN:
                    board.moveBricks(Board.MoveDirection.Down);
                    break;
                case KeyEvent.VK_SPACE:
                    board.swapBricks();
                    break;
                case KeyEvent.VK_ESCAPE:
                    pause = true;
                    infoPanel.togglePauseMsg();
                    break;
            }
        }
        repaint();
    }
}
