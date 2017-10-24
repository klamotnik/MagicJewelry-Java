package SEngine.Components.Levels;

import SEngine.Components.Logo;
import SEngine.Components.ScoreItems;
import SEngine.LevelManager;
import SEngine.Types.Level;
import SEngine.Utils.FileHelper;

import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class Scores extends Level {

    private ScoreItems scoreItem;

    public Scores() {
        super();
        add(new Logo(164, 50, true));
        scoreItem = new ScoreItems(0, 200);
        add(scoreItem);
        loadScores();
    }

    private void loadScores() {
        try {
            ArrayList<String> items = FileHelper.readScoreFromFile();
            ArrayList<String> extraList = new ArrayList<>();

            if (items.size() > 5) {
                Collections.sort(items);
                Collections.reverse(items);
                for (int i = 0; i < 5; i++) {
                    extraList.add(items.get(i));
                }
            } else {
                extraList = items;
            }

            scoreItem.setItems(extraList);
            scoreItem.repaint();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        LevelManager.getInstance().changeLevel(MainMenu.class);
    }
}
