package SEngine.Interfaces;

/**
 * Główny interfejs aplikacji. Wykorzystywany jest przez silnika, aby zarządzać zasobami aplikacji.
 */

public interface Tickable {
    boolean canTick();

    void tick(int deltaTime);
}
