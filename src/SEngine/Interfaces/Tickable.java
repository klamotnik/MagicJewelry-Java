package SEngine.Interfaces;

public interface Tickable {
    boolean canTick();

    void tick(int deltaTime);
}
