package scoreboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scoreboard.exceptions.TeamAlreadyInMatchException;

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

        if (checkIfTeamHasStartedMatch(homeTeam)) {
            log.error("Team {} has already started a match!", homeTeam);
            throw new TeamAlreadyInMatchException();
        } else if ( checkIfTeamHasStartedMatch(awayTeam)) {
            log.error("Team {} has already started a match!", awayTeam);
            throw new TeamAlreadyInMatchException();
        }

        scores.put(homeTeam, awayTeam);

        log.info("Game for {} and {} added successfully", homeTeam, awayTeam);
    }

    private boolean checkIfTeamHasStartedMatch(String team) {
        return scores.containsKey(team) || scores.containsValue(team);
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
