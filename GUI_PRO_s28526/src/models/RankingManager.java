package models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RankingManager {
    private static final String RANKING_FILE = "ranking.dat";

    public static void saveScore(RankingEntry entry) throws IOException {
        List<RankingEntry> scores = loadScores();
        scores.add(entry);


        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RANKING_FILE))) {
            oos.writeObject(scores);
        }
    }


    public static List<RankingEntry> loadScores() {
        File f = new File(RANKING_FILE);
        if (!f.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            return (List<RankingEntry>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
