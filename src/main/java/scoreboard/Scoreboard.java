package scoreboard;

import java.util.HashMap;
import java.util.Map;

public class Scoreboard {

    private final Map<String, String> scores;

    public Scoreboard() {
        scores = new HashMap<>();
    }

    public void startNewGame(String homeTeam, String awayTeam) {
    }

    public void updateScore(String homeTeam, String awayTeam) {

    }

    public void finishGame(String homeTeam, String awayTeam) {

    }

    public void getSummary() {

    }

    Map<String, String> getScores() {
        return scores;
    }
}
