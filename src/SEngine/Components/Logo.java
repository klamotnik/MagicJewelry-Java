package SEngine.Components;

import SEngine.Types.Actor;
import SEngine.Types.ImagePanel;

import java.util.Random;

public class Logo extends Actor {
    public enum LogoColor {
        Blank(0),
        Orange(1),
        Gray(2),
        Violet(3),
        Aqua(4),
        Pink(5),
        Green(6),
        Yellow(7),
        Blue(8);

        private int nr;

        LogoColor(int nr) {
            this.nr = nr;
        }

        public int value() {
            return nr;
        }

        public static LogoColor fromValue(int nr) {
            try {
                return values()[nr];
            } catch (ArrayIndexOutOfBoundsException e) {
                return Blank;
            }
        }
    }

    private boolean animate;
    private LogoColor currentColor;
    private int timeFromChange;

    private static ImagePanel[] sprites = new ImagePanel[]{
            new ImagePanel("logo/0.png"),
            new ImagePanel("logo/1.png", 2, 2),
            new ImagePanel("logo/2.png", 2, 2),
            new ImagePanel("logo/3.png", 2, 2),
            new ImagePanel("logo/4.png", 2, 2),
            new ImagePanel("logo/5.png", 2, 2),
            new ImagePanel("logo/6.png", 2, 2),
            new ImagePanel("logo/7.png", 2, 2),
            new ImagePanel("logo/8.png", 2, 2)
    };

    public Logo(int x, int y, boolean animate) {
        super(x, y);
        currentColor = LogoColor.Orange;
        setSize(311, 122);
        add(sprites[0]);
        add(sprites[1], 0);
        this.animate = animate;
        timeFromChange = 0;
    }

    public boolean changeColor(LogoColor color) {
        if (currentColor == color)
            return false;
        currentColor = color;
        repaint();
        return true;
    }

    @Override
    public boolean canTick() {
        return animate;
    }

    @Override
    public void tick(int deltaTime) {
        if (animate) {
            timeFromChange += deltaTime;
            if (timeFromChange > 500) {
                while (!changeColor(LogoColor.fromValue(new Random().nextInt(8) + 1))) ;
                repaint();
                timeFromChange = 0;
            }
        }
    }

    @Override
    public void repaint() {
        if (currentColor != null) {
            remove(0);
            add(sprites[currentColor.value()], 0);
        }
        super.repaint();
    }
}
