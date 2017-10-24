package SEngine;

import SEngine.Types.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class SEngine implements ActionListener, KeyListener {
    private Window window;
    private Level currentLevel;
    private int timerDelay = 20;
    private long lastTimestamp = 0;
    private LevelManager levelManager;

    public SEngine() {
        window = new Window(640, 480, "MagicJewelry");
        init();
    }

    private void init() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getClassLoader().getResourceAsStream("font/xolonium.ttf")));
        } catch (Exception ex) {

        }
    }

    public void start() {
        Timer t = new Timer(timerDelay, this);
        lastTimestamp = System.currentTimeMillis();
        levelManager = LevelManager.getInstance();
        window.repaint();
        window.addKeyListener(this);
        t.start();
    }

    public void actionPerformed(ActionEvent event) {
        Level level = levelManager.getCurrentLevel();
        if (level != currentLevel) {
            if (currentLevel != null)
                window.remove(currentLevel);
            window.add(level);
            window.repaint();
            currentLevel = level;
        }
        long currentTimestamp = event.getWhen();
        level.tick((int) (currentTimestamp - lastTimestamp));
        lastTimestamp = currentTimestamp;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        currentLevel.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) {

    }
}
