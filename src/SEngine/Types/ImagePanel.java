package SEngine.Types;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ImagePanel extends JPanel {
    private static ArrayList<BufferedImage> imageLibrary;
    private static ArrayList<String> imageLibraryPathes;
    private int ix;

    public ImagePanel(String path, int x, int y) {
        if (imageLibrary == null){
            imageLibrary = new ArrayList<>(1);
            imageLibraryPathes = new ArrayList<>(1);
        }
        try {
            BufferedImage image = null;
            if(!imageLibraryPathes.contains(path)){
                imageLibrary.add(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(path)));
                imageLibraryPathes.add(path);
            }
            image = imageLibrary.get(imageLibraryPathes.indexOf(path));
            setSize(image.getWidth(), image.getHeight());
            setBackground(new Color(0, 0, 0, 0));
            setLocation(x, y);
            setVisible(true);
            ix = imageLibrary.size();
            imageLibrary.add(image);
            imageLibraryPathes.add(path);
        } catch (IOException ex) {

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