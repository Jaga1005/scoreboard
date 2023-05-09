package scoreboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class Scoreboard {
    private static final Logger log = LogManager.getLogger("scoreboard");
    private final Map<String, String> scores;

    public Scoreboard() {
        scores = new HashMap<>();
    }

    public void startNewGame(String homeTeam, String awayTeam) {
        log.info("Starting new game for homeTeam: {} and awayTeam: {}", homeTeam, awayTeam);

        scores.put(homeTeam, awayTeam);

        log.info("Game for {} and {} added successfully", homeTeam, awayTeam);
    }

    public void updateScore(String homeTeam, String awayTeam) {
        throw new UnsupportedOperationException();
    }

    public void finishGame(String homeTeam, String awayTeam) {
        throw new UnsupportedOperationException();
    }

    public void getSummary() {
        throw new UnsupportedOperationException();
    }

    Map<String, String> getScores() {
        return scores;
    }
}
