package SEngine;

import SEngine.Components.Levels.MainMenu;
import SEngine.Types.Level;

/**
 * Klasa LevelManager jest odpowiedzialna za zarządzanie Level'ami.
 * Posiada matody umożliwiające pobranie aktualnego level'a oraz metodę do zmiany level'a.
 **/

public class LevelManager {
    private static LevelManager levelManager;
    private Level currentLevel;

    public static LevelManager getInstance() {
        if (levelManager == null) {
            levelManager = new LevelManager();
            levelManager.changeLevel(MainMenu.class);
        }
        return levelManager;
    }

    public void changeLevel(Class<? extends Level> levelType) {
        Level level = null;
        try {
            level = levelType.newInstance();
        } catch (Exception ex) {

        }
        if (level != null)
            currentLevel = level;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    private LevelManager() {
    }
}
