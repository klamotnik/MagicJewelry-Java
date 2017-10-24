package SEngine.Components;

import SEngine.Types.Actor;

import java.awt.*;

/**
 * Klasa InfoPanel jest odpowiedzialna za stowrzenie obszaru z danymi na temat aktualnego
 * stanu gry oraz jego aktualizację.
 */

public class InfoPanel extends Actor {
    private int level;
    private int score;
    private int bricks;
    private boolean isGameOver;

    public InfoPanel(int x, int y) {
        super(x, y);
        level = 1;
        score = 0;
        bricks = 0;
        setSize(300, 100);
        repaint();
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getBricks() {
        return bricks;
    }

    public void addLevel() {
        level++;
        repaint();
    }

    public void addPoints(int valueToAdd) {
        score += valueToAdd;
        repaint();
    }

    public void addBricks(int valueToAdd) {
        bricks += valueToAdd;
        repaint();
    }

    public void setGameOverMsg() {
        isGameOver = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Xolonium", Font.PLAIN, 20));
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        g2d.setColor(Color.WHITE);
        g2d.drawString("Score:", 16, 20);
        g2d.drawString(String.format("%1$d", score), 92, 20);
        g2d.drawString("Bricks:", 12, 40);
        g2d.drawString(String.format("%1$d", bricks), 92, 40);
        g2d.drawString("Level:", 20, 60);
        g2d.drawString(String.format("%1$d", level), 92, 60);

        if (isGameOver) {
            g2d.setColor(Color.RED);
            g2d.drawString("Game Over", 0, 80);
        }

    }
}
