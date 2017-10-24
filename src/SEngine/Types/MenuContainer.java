package SEngine.Types;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * Klasa MenuContainer jest obszarem, w którym możemy dodawać elemnty menu, które może wybrać/aktywować gracz.
 */

public class MenuContainer extends Actor {
    private MenuElement activeElement;

    public MenuContainer(int x, int y) {
        super(x, y);
        setBackground(Color.BLACK);
        setSize(200, 0);
        setVisible(true);
        activeElement = null;
    }

    public void SetActiveElement(MenuElement element) {
        activeElement.setActive(false);
        element.setActive(true);
        activeElement = element;
    }

    public boolean addElement(String name, String caption, Callable action) {
        for (Component c : getComponents()) {
            if (c instanceof MenuElement) {
                if (((MenuElement) c).getName() == name)
                    return false;
            }
        }
        MenuElement me = new MenuElement(name, caption, action);
        if (getComponents().length == 0) {
            me.setActive(true);
            activeElement = me;
        }
        add(me);
        repaint();
        if (me.getWidth() > getWidth()) {
            me.setLocation(0, getHeight());
            setSize(me.getWidth(), getHeight() + me.getHeight());
        } else {
            me.setLocation((getWidth() - me.getWidth()) / 2, getHeight());
            setSize(getWidth(), getHeight() + me.getHeight());
        }
        repaint();
        return true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int index = Arrays.asList(getComponents()).indexOf(activeElement);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_UP:
                activeElement.setActive(false);
                activeElement = (MenuElement) getComponents()[e.getKeyCode() == KeyEvent.VK_DOWN ? (index + 1 >= getComponents().length ? 0 : index + 1) : index - 1 <= -1 ? getComponents().length - 1 : index - 1];
                activeElement.setActive(true);
                break;
            case KeyEvent.VK_ENTER:
                activeElement.doAction();
                break;
        }
        repaint();
    }
}
