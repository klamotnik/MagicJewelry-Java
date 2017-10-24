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

/**
 * Klasa Scores jest odpowiedzialna za wyświetlenie Loga oraz wyników jakie uzyskał gracz.
 * Posiada prywatną metodę loadScores, która ładuje dane do wyświetlenia przy pomocy FileHelper'a.
 */

public class Scores extends Level {
//    private class Score{
//        public String playerName;
//        public int bricks;
//        public int score;
//
//        public Score(String playerName, int bricks, int score){
//            this.playerName = playerName;
//            this.bricks = bricks;
//            this.score = score;
//        }
//    }

    private ScoreItems scoreItem = new ScoreItems(200, 200);

    public Scores() {
        super();
        add(new Logo(164, 50, true));
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
