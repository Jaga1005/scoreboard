package scoreboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import scoreboard.exceptions.MatchAlreadyStartedException;
import scoreboard.exceptions.NotUniquePairException;
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

        validateTeamsNames(homeTeam, awayTeam);
        validateExistingGames(homeTeam, awayTeam);

        scores.put(homeTeam, awayTeam);

        log.info("Game for {} and {} added successfully", homeTeam, awayTeam);
    }

    private void validateExistingGames(String homeTeam, String awayTeam) {
        checkIfTeamsHaveAlreadyStartedGame(homeTeam, awayTeam);
        checkIfTeamHasAlreadyStartedDifferentGame(homeTeam);
        checkIfTeamHasAlreadyStartedDifferentGame(awayTeam);
    }

    private void checkIfTeamHasAlreadyStartedDifferentGame(String team) {
        if (checkIfTeamHasStartedGame(team)) {
            log.error("Team {} has already started a match!", team);
            throw new TeamAlreadyInMatchException();
        }
    }

    private void checkIfTeamsHaveAlreadyStartedGame(String homeTeam, String awayTeam) {
        if (scores.containsKey(homeTeam) && scores.get(homeTeam).equals(awayTeam)) {
            log.error("Teams {} and {} have already started a match!", homeTeam, awayTeam);
            throw new MatchAlreadyStartedException();
        }
    }

    private static void validateTeamsNames(String homeTeam, String awayTeam) {
        validateTeamName(homeTeam);
        validateTeamName(awayTeam);
        validateIfTeamsAreDifferent(homeTeam, awayTeam);
    }

    private static void validateIfTeamsAreDifferent(String homeTeam, String awayTeam) {
        if (homeTeam.equalsIgnoreCase(awayTeam)) {
            log.error("HomeTeam cannot be the same as awayTeam!");
            throw new NotUniquePairException();
        }
    }

    private static void validateTeamName(String homeTeam) {
        if (Strings.isEmpty(homeTeam)) {
            log.error("Team name cannot be empty!");
            throw new IllegalArgumentException();
        }
    }

    private boolean checkIfTeamHasStartedGame(String team) {
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
