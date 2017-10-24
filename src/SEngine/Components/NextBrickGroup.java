package SEngine.Components;

import SEngine.Types.Actor;

import java.util.Random;

/**
 * Klasa NextBrickGroup odpowiedzialna jest na wyświetlenie grupy kolejnych klocków, która zostanie dodana do gry.
 */

public class NextBrickGroup extends Actor {
    private Brick[] nextBrickGroup;

    public NextBrickGroup(int x, int y) {
        super(x, y);
        setSize(30, 90);
        nextBrickGroup = new Brick[3];
        drawNextGroup();
    }

    public Brick[] drawNextGroup() {
        Brick bricksToReturn[] = nextBrickGroup;
        removeAll();
        nextBrickGroup = new Brick[3];
        for (int i = 0; i < 3; ++i) {
            nextBrickGroup[i] = new Brick(0, 60 - 30 * i, Brick.BrickColor.fromValue(new Random().nextInt(6) + 1));
            add(nextBrickGroup[i]);
        }
        repaint();
        return bricksToReturn;
    }

    @Override
    public void hide() {
        removeAll();
        repaint();
    }

    @Override
    public boolean canTick() {
        return false;
    }
}
