package scoreboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scoreboard.exceptions.MatchAlreadyStartedException;
import scoreboard.exceptions.MatchDoesntExistException;
import scoreboard.exceptions.TeamAlreadyInMatchException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static scoreboard.TeamValidator.validateTeamsNames;

public class Scoreboard {
    private static final Logger log = LogManager.getLogger("Scoreboard");
    private final Map<String, Match> matches;

    public Scoreboard() {
        matches = new HashMap<>();
    }

    /**
     * Adds new game to Scoreboard.
     * Note: It converts given team names to upper case.
     * It throws an IllegalArgumentExceptions if either homeTeam or awayTeam name are null or empty
     * It throws a NotUniquePairException if teams names are the same
     * It throws a MatchAlreadyStartedException if match for homeTeam and AwayTeam already exists
     * It throws a TeamAlreadyInMatchException if homeTeam or awayTeam has already different match in progress
     *
     * @param homeTeam
     * @param awayTeam
     */
    public void startNewGame(String homeTeam, String awayTeam) {
        log.info("Starting new game for homeTeam: {} and awayTeam: {}", homeTeam, awayTeam);

        validateTeamsNames(homeTeam, awayTeam);

        homeTeam = homeTeam.toUpperCase();
        awayTeam = awayTeam.toUpperCase();

        validateExistingGames(homeTeam, awayTeam);

        matches.put(homeTeam, Match.newTeam(homeTeam, awayTeam));

        log.info("Game for {} and {} added successfully", homeTeam, awayTeam);
    }

    private void validateExistingGames(String homeTeam, String awayTeam) {
        log.info("Validate existing games");

        checkIfTeamsHaveAlreadyStartedGame(homeTeam, awayTeam);
        checkIfTeamHasAlreadyStartedDifferentGame(homeTeam);
        checkIfTeamHasAlreadyStartedDifferentGame(awayTeam);
    }

    private void checkIfTeamsHaveAlreadyStartedGame(String homeTeam, String awayTeam) {
        if (matches.containsKey(homeTeam) && checkIfHomeTeamIsPlayingWithAwayTeam(homeTeam, awayTeam)) {
            log.error("Teams {} and {} have already started a match!", homeTeam, awayTeam);
            throw new MatchAlreadyStartedException();
        }
    }

    private boolean checkIfHomeTeamIsPlayingWithAwayTeam(String homeTeam, String awayTeam) {
        return matches.get(homeTeam).getAwayTeam().equals(awayTeam);
    }

    private void checkIfTeamHasAlreadyStartedDifferentGame(String team) {
        if (checkIfTeamHasStartedGame(team)) {
            log.error("Team {} has already started a match!", team);
            throw new TeamAlreadyInMatchException();
        }
    }

    private boolean checkIfTeamHasStartedGame(String team) {
        return matches.containsKey(team) || checkIfTeamPlaysAsAwayTeam(team);
    }

    private boolean checkIfTeamPlaysAsAwayTeam(String team) {
        return matches.values().stream().anyMatch(t -> t.getAwayTeam().equals(team));
    }

    /**
     * Updates existing game score.
     * Note: It converts given team names to upper case.
     * It validates teams names
     * It throws a MatchDoesntExistException if homeTeam doesn't have any match
     * It throws a MatchDoesntExistException if match between homeTeam and awayTeam doesn't exist
     *
     * @param homeTeam
     * @param awayTeam
     * @param homeScore
     * @param awayScore
     */
    public void updateGame(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        log.info("Update score of a match {} {} - {} {}", homeTeam, homeScore, awayTeam, awayScore);

        validateTeamsNames(homeTeam, awayTeam);

        homeTeam = homeTeam.toUpperCase();
        awayTeam = awayTeam.toUpperCase();

        validateIfHomeTeamExists(homeTeam);
        validateIfMatchExists(homeTeam, awayTeam);

        matches.get(homeTeam).updateScore(homeScore, awayScore);

        log.info("Match score updated!");
    }

    private void validateIfHomeTeamExists(String homeTeam) {
        if (!matches.containsKey(homeTeam)) {
            log.error("HomeTeam {} doesn't exist!", homeTeam);
            throw new MatchDoesntExistException();
        }
    }

    private void validateIfMatchExists(String homeTeam, String awayTeam) {
        if (!checkIfHomeTeamIsPlayingWithAwayTeam(homeTeam, awayTeam)) {
            log.error("Match between HomeTeam {} and AwayTeam {} doesn't exist", homeTeam, awayTeam);
            throw new MatchDoesntExistException();
        }
    }

    /**
     * Removes existing game from Scoreboard
     * Note: It converts given team names to upper case.
     * It throws an IllegalArgumentExceptions if either homeTeam or awayTeam name is null or empty
     * It throws a NotUniquePairException if teams names are the same
     * It throws a MatchDoesntExistException if homeTeam doesn't have any match
     * It throws a MatchDoesntExistException if match between homeTeam and awayTeam doesn't exist
     *
     * @param homeTeam
     * @param awayTeam
     */
    public void finishGame(String homeTeam, String awayTeam) {
        log.info("Finish game between {} and {}", homeTeam, awayTeam);

        validateTeamsNames(homeTeam, awayTeam);

        homeTeam = homeTeam.toUpperCase();
        awayTeam = awayTeam.toUpperCase();

        validateIfHomeTeamExists(homeTeam);
        validateIfMatchExists(homeTeam, awayTeam);

        matches.remove(homeTeam);
    }

    /**
     * Sorts existing matches by total score in descending order, then sorts by start time in ascending order
     *
     * @return sorted list of existing matches
     */
    public List<Match> getSummary() {
        log.info("Get scoreboard summary");

        List<Match> list = new ArrayList<>(matches.values());
        list.sort(new MatchComparator());

        return list;
    }

    Map<String, Match> getScores() {
        return matches;
    }
}
