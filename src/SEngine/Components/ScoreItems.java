package SEngine.Components;

import SEngine.Types.Actor;

import java.awt.*;
import java.util.ArrayList;

/**
 * Klasa ScoreItems odpowiedzialna jest za stworzenie obszaru, w którym wyświetlone zostaną wyniki gracza.
 */
public class ScoreItems extends Actor {

    private ArrayList<String> items;

    public ScoreItems(int x, int y) {
        super(x, y);
        setSize(240, 230);
        repaint();
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
        repaint();
    }

    @Override
    public boolean canTick() {
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Xolonium", Font.PLAIN, 20));
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        g2d.setColor(Color.WHITE);
        int y = 20;
        g2d.drawString("Score:", 90, y);
        for (int i = 0; i < items.size(); i++) {
            g2d.drawString(items.get(i), 90, y += 20);
        }
    }
}
