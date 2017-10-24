package SEngine;

import javax.swing.*;
import java.awt.*;

/**
 * Klasa Window odpowiedzialna jest za utworzenie okna aplikacji.
 **/

public class Window extends JFrame {
    public Window(int width, int height, String title) {
        setTitle(title);
        setSize(width, height);
        getContentPane().setPreferredSize(new Dimension(width, height));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.BLACK);
        pack();
        setVisible(true);
    }
}