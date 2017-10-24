package SEngine.Types;

import SEngine.Interfaces.Tickable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Animation implements Tickable {
    protected List<Actor> actorsToManipulation;
    private boolean pause;
    private boolean canTick;

    protected Animation(Actor parent) {
        actorsToManipulation = new ArrayList<>();
        actorsToManipulation.add(parent);
    }

    protected Animation(Collection<? extends Actor> parent) {
        actorsToManipulation = new ArrayList<>();
        actorsToManipulation.addAll(parent);
    }

    public boolean canTick() {
        return canTick;
    }

    public abstract boolean isPending();

    public abstract boolean isCompleted();

    public boolean isPaused() {
        return pause;
    }

    public boolean togglePause() {
        return (pause = !pause);
    }

    public void start() {
        canTick = true;
    }

    public void stop() {
        canTick = false;
    }
}
