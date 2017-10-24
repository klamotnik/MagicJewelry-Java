package SEngine.Components.Animations;

import SEngine.Components.Brick;
import SEngine.Types.Actor;
import SEngine.Types.Animation;

import java.util.Collection;

public class BrickAnimation extends Animation {

    static final int ANIMATION_STEPS = 18;
    static final int ANIMATION_INTERVAL = 100;
    int animationCounter = 0;

    public BrickAnimation(Brick parent) {
        super(parent);
    }

    public BrickAnimation(Collection<Brick> parent) {
        super(parent);
    }

    public void tick(int deltaTime) {
        if (!isCompleted()) {
            animationCounter += deltaTime;
            Brick.BrickColor computedColor = Brick.BrickColor.fromValue((animationCounter / ANIMATION_INTERVAL) % 6 + 1);
            for (Actor b : actorsToManipulation)
                ((Brick) b).changeColor(computedColor);
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
    }
}
