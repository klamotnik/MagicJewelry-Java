package SEngine.Components;

import SEngine.Types.Actor;

import java.awt.*;
import java.util.ArrayList;

public class ScoreItems extends Actor {

    private ArrayList<String> items;

    public ScoreItems(int x, int y) {
        super(x, y);
        setSize(640, 230);
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
        g2d.drawString("Score", 150, y);
        g2d.drawString("Jewelry", 350, y);
        for (int i = 0; i < items.size(); i++) {
            String[] splittedItem = items.get(i).split("-");
            g2d.drawString(splittedItem[0], 150, y += 24);
            g2d.drawString(splittedItem[1], 350, y);
        }
    }
}
