package SEngine.Types;

import java.awt.*;
import java.io.File;
import java.util.concurrent.Callable;

/**
 * Klasa MenuElement jest pozycją na liście w menu.
 * Umożliwia przypisanie akcji po kliknięciu na pozycję.
 */

public class MenuElement extends Actor {
    private String name;
    private String caption;
    private boolean active;
    private Callable<Void> action;
    private Font font;

    public MenuElement(String name, String caption, Callable action) {
        super(0, 0);
        this.name = name;
        this.caption = caption;
        this.action = action;
        this.active = false;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("font/xolonium.ttf")).deriveFont(28f);
        } catch (Exception ex) {

        }
        setSize(100, 30);
        repaint();
    }

    public void setActive(boolean active) {
        this.active = active;
        repaint();
    }

    public void doAction() {
        if (action == null)
            return;
        try {
            action.call();
        } catch (Exception ex) {

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Xolonium", Font.PLAIN, 20));
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        g2d.setColor(active ? Color.RED : Color.lightGray);
        g2d.drawString(caption, 0, 20);
        setSize(g2d.getFontMetrics().stringWidth(caption), g2d.getFontMetrics().getHeight());
        setLocation((getParent().getWidth() - getWidth()) / 2, getY());
    }
}
