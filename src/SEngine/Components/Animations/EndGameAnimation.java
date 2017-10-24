package SEngine.Components.Animations;

import SEngine.Components.Board;
import SEngine.Components.Brick;
import SEngine.Types.Animation;

public class EndGameAnimation extends Animation {
    static final int ANIMATION_STEPS = Board.BRICKS_IN_COL;
    static final int ANIMATION_INTERVAL = 200;
    int animationCounter = 0;
    int animationStep = 0;

    public EndGameAnimation(Board parent) {
        super(parent);
    }

    public void tick(int deltaTime) {
        if (!isCompleted()) {
            animationCounter += deltaTime;
            if (animationCounter > animationStep * ANIMATION_INTERVAL) {
                animationStep++;
                setRow();
            }
        }
    }

    private void setRow() {
        Board board = (Board) actorsToManipulation.get(0);
        boolean[] row = new boolean[Board.BRICKS_IN_ROW];
        for (Object o : board.getComponents()) {
            if (o instanceof Brick) {
                Brick brick = (Brick) o;
                if (board.getBrickY(brick.getY()) < animationStep && brick.getColor() != Brick.BrickColor.Special) {
                    int x = board.getBrickX(brick.getX());
                    row[x] = true;
                    brick.changeColor(Brick.BrickColor.Special);
                }
            }
        }
        for (int i = 0; i < Board.BRICKS_IN_ROW; ++i) {
            if (!row[i]) {
                Brick b = new Brick(Brick.BrickColor.Special);
                b.setBrickPosition(i, animationStep - 1);
                board.add(b, 0);
            }
        }
    }

    public boolean isPending() {
        return canTick() && !isPaused() && animationCounter < ANIMATION_STEPS * ANIMATION_INTERVAL;
    }

    public boolean isCompleted() {
        return canTick() && animationCounter >= ANIMATION_STEPS * ANIMATION_INTERVAL;
    }

    @Override
    public void stop() {
        super.stop();
        animationCounter = 0;
        animationStep = 0;
    }
}
