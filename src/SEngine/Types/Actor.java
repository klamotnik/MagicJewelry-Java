package SEngine.Types;

import SEngine.Interfaces.Tickable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Klasa Actor jest jednym z podstawowych "rozszerzeń" obiektów w aplikacji.
 * Actor'zy wyświetlani są na Level'u.
 * Na Level'u można wyświetlić wielu Actor'ów.
 */

public abstract class Actor extends JPanel implements Tickable, KeyListener {
    protected Actor(int x, int y) {
        setBackground(Color.black);
        setLocation(x, y);
        setLayout(null);
        setVisible(true);
    }

    public boolean canTick() {
        return true;
    }

    public void tick(int deltaTime) {
        for (Component c : getComponents()) {
            if (c instanceof Tickable) {
                if (((Tickable) c).canTick())
                    ((Tickable) c).tick(deltaTime);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}
