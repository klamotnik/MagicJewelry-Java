package SEngine.Types;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Klasa ImagePanel jest klasą pomocniczą wykorzystywaną w Logo, Brick oraz Board.
 * Umożliwia prostą implementację resources'ów do aplikacji.
 */

public class ImagePanel extends JPanel {
    private static ArrayList<BufferedImage> imageLibrary;
    private int ix;

    public ImagePanel(String path, int x, int y) {
        if (imageLibrary == null)
            imageLibrary = new ArrayList<>(1);
        try {
            BufferedImage image = ImageIO.read(new File(path));
            setSize(image.getWidth(), image.getHeight());
            setBackground(new Color(0, 0, 0, 0));
            setLocation(x, y);
            setVisible(true);
            ix = imageLibrary.size();
            imageLibrary.add(image);
        } catch (IOException ex) {
            // handle exception...
        }

    }

    public ImagePanel(String path) {
        this(path, 0, 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageLibrary.get(ix), 0, 0, this);
    }

}