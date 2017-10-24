package SEngine.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHelper {

    public static final String SCORE_TXT = "score.txt";

    public static void saveScoreToFile(int score, int bricks) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(SCORE_TXT, true));
            bw.write(String.format("%d-%d", score, bricks));
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static FileWriter createScoreFileIfNecessary(){
        try {
            return new FileWriter(SCORE_TXT, true);
        } catch(IOException ex) {
            return null;
        }
    }

    public static ArrayList<String> readScoreFromFile() throws FileNotFoundException {
        createScoreFileIfNecessary();
        Scanner s = new Scanner(new File(SCORE_TXT));
        ArrayList<String> list = new ArrayList<>();
        while (s.hasNext()) {
            list.add(s.next());
        }
        s.close();
        return list;
    }
}
