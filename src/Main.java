import SEngine.SEngine;

/**
 * Klasa Main jest główną klasą tego projektu.
 * Wystartowany zostaje tu silnik, który steruje całą aplikacją.
 **/
public class Main {
    public static void main(String[] args) {
        SEngine engine = new SEngine();
        engine.start();
    }
}