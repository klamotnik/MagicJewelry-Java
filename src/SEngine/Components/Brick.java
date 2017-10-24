package SEngine.Components;

import SEngine.Types.Actor;
import SEngine.Types.ImagePanel;

public class Brick extends Actor {
    public enum BrickColor {
        Special(0),
        Green(1),
        Blue(2),
        Orange(3),
        Violet(4),
        Yellow(5),
        Red(6);

        private int nr;

        BrickColor(int nr) {
            this.nr = nr;
        }

        public int value() {
            return nr;
        }

        public static BrickColor fromValue(int nr) {
            try {
                return values()[nr];
            } catch (ArrayIndexOutOfBoundsException e) {
                return Green;
            }
        }
    }

    private ImagePanel[] sprites = new ImagePanel[]{
            new ImagePanel("bricks/b_0.png"),
            new ImagePanel("bricks/b_1.png"),
            new ImagePanel("bricks/b_2.png"),
            new ImagePanel("bricks/b_3.png"),
            new ImagePanel("bricks/b_4.png"),
            new ImagePanel("bricks/b_5.png"),
            new ImagePanel("bricks/b_6.png"),
    };
    static final int BRICK_WIDTH = 30;
    static final int BRICK_HEIGHT = 30;
    private BrickColor color;

    public boolean toRemove;

    public Brick(int x, int y, BrickColor color) {
        super(x, y);
        this.color = color;
        toRemove = color == BrickColor.Special;
        setSize(BRICK_WIDTH, BRICK_HEIGHT);
        add(sprites[color.value()]);
    }

    public boolean changeColor(BrickColor color) {
        this.color = color;
        repaint();
        return true;
    }

    public Brick(BrickColor color) {
        this(0, 0, color);
    }

    public void setBrickPosition(int x, int y) {
        setLocation(x * BRICK_WIDTH + Board.BOARD_OFFSET_X, (Board.BRICKS_IN_COL - 1) * BRICK_HEIGHT - y * BRICK_HEIGHT + Board.BOARD_OFFSET_Y);
        setVisible(y <= 14);
    }

    public BrickColor getColor() {
        return color;
    }

    @Override
    public void repaint() {
        if (color != null) {
            removeAll();
            add(sprites[color.value()], 0);
        }
        super.repaint();
    }
}
