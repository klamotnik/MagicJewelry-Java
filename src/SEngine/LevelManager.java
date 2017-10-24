package SEngine;

import SEngine.Components.Levels.MainMenu;
import SEngine.Types.Level;

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
