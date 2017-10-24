package SEngine.Components.Levels;

import SEngine.Components.Logo;
import SEngine.LevelManager;
import SEngine.Types.Level;
import SEngine.Types.MenuContainer;

/**
 * Klasa MainMenu jest odpowiedzialna za wyświetlenie Loga oraz opcji jakie posiada gracz.
 * Deleguje do kolejnych level'i.
 */

public class MainMenu extends Level {
    public MainMenu() {
        super();
        add(new Logo(164, 50, true));
        MenuContainer mc = new MenuContainer(220, 230);
        mc.addElement("play", "Play", () -> {
            LevelManager.getInstance().changeLevel(Game.class);
            return null;
        });
        mc.addElement("scores", "Scores", () -> {
            LevelManager.getInstance().changeLevel(Scores.class);
            return null;
        });
        mc.addElement("exit", "Exit", () -> {
            System.exit(0);
            return null;
        });
        add(mc);
        repaint();
    }
}
