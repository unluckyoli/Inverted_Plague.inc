package models;

import java.io.Serializable;

public class RankingEntry implements Serializable {
    private String playerName;
    private int timeSec;
    private int score;

    private Difficulty difficulty;

    private String result;





    public RankingEntry(String playerName, int timeSec, int score, Difficulty difficulty, String result) {
        this.playerName = playerName;
        this.timeSec = timeSec;
        this.score = score;
        this.difficulty = difficulty;
        this.result = result;
    }

    public String getPlayerName() { return playerName; }
    public int getTimeSec() { return timeSec; }
    public int getScore() { return score; }
    public Difficulty getDifficulty() { return difficulty; }
    public String getResult() { return result; }



    @Override
    public String toString() {
        int mm = timeSec / 60;
        int ss = timeSec % 60;
        String string = String.format("%02d:%02d", mm, ss);
        return String.format("%s [%s] - %s | score=%d, time=%s",
                playerName, difficulty, result, score, string);
    }
}
