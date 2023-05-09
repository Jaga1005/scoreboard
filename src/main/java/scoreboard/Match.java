package scoreboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class Match {
    private static final Logger log = LogManager.getLogger("match");
    private final String homeTeam;
    private final String awayTeam;
    private final long startTime;
    private int homeTeamScore;
    private int awayTeamScore;

    public static Match newTeam(String homeTeam, String awayTeam) {
        return new Match(homeTeam, awayTeam);
    }

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = System.nanoTime();
        homeTeamScore = 0;
        awayTeamScore = 0;
    }

    Match(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.startTime = System.nanoTime();
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public long getStartTime() {
        return startTime;
    }

    public void updateScore(int homeScore, int awayScore) {
        log.info("Update score of a match");

        validateScores(homeScore, awayScore);

        this.homeTeamScore = homeScore;
        this.awayTeamScore = awayScore;
    }

    private static void validateScores(int homeScore, int awayScore) {
        log.info("Validate scores");

        validateScore(homeScore);
        validateScore(awayScore);
    }

    private static void validateScore(int score) {
        if (score < 0) {
            log.error("Score for team cannot be a negative number! Current value: {}", score);
            throw new IllegalArgumentException();
        }
    }

    public int getTotalScore() {
        return homeTeamScore + awayTeamScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Match match = (Match) o;

        return homeTeamScore == match.homeTeamScore
                && awayTeamScore == match.awayTeamScore
                && Objects.equals(homeTeam, match.homeTeam)
                && Objects.equals(awayTeam, match.awayTeam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam, homeTeamScore, awayTeamScore);
    }
}
