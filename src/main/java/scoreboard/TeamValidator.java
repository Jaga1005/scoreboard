package scoreboard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import scoreboard.exceptions.NotUniquePairException;

public class TeamValidator {

    private static final Logger log = LogManager.getLogger("TeamValidator");

    private TeamValidator() {
    }

    /**
     * Validates teams names
     * It throws an IllegalArgumentExceptions if either homeTeam or awayTeam name is null or empty
     * It throws a NotUniquePairException if teams names are the same
     *
     * @param homeTeam
     * @param awayTeam
     */
    public static void validateTeamsNames(String homeTeam, String awayTeam) {
        log.info("Validate team names");

        validateTeamName(homeTeam);
        validateTeamName(awayTeam);
        validateIfTeamsAreDifferent(homeTeam, awayTeam);
    }

    private static void validateTeamName(String team) {
        if (Strings.isEmpty(team)) {
            log.error("Team name cannot be empty!");
            throw new IllegalArgumentException();
        }
    }

    private static void validateIfTeamsAreDifferent(String homeTeam, String awayTeam) {
        if (homeTeam.equalsIgnoreCase(awayTeam)) {
            log.error("HomeTeam cannot be the same as awayTeam!");
            throw new NotUniquePairException();
        }
    }

}
