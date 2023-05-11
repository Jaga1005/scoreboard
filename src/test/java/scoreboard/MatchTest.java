package scoreboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatchTest {
    private static final String HOME_TEAM_NAME = "TeamA";
    private static final String AWAY_TEAM_NAME = "TeamB";

    @Test
    void whenUpdateScore_givenProperValues_thenUpdateTheGame() {
        //given
        Match match = new Match(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //when
        match.updateScore(100, 20);

        //then
        int expectedHome = 100;
        int expectedAway = 20;

        assertEquals(expectedHome, match.getHomeTeamScore());
        assertEquals(expectedAway, match.getAwayTeamScore());
    }

    @Test
    void whenUpdateScore_givenZeroValue_thenUpdateTheGame() {
        //given
        Match match = new Match(HOME_TEAM_NAME, AWAY_TEAM_NAME, 2, 3);

        //when
        match.updateScore(1, 0);

        //then
        int expectedHome = 1;
        int expectedAway = 0;

        assertEquals(expectedHome, match.getHomeTeamScore());
        assertEquals(expectedAway, match.getAwayTeamScore());
    }

    @Test
    void whenUpdateGame_givenNegativeNumberOnHomeTeamScore_thenThrowException() {
        //given
        Match match = new Match(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            match.updateScore(-1, 2);
        });
    }

    @Test
    void whenUpdateGame_givenNegativeNumberOnAwayTeamScore_thenThrowException() {
        //given
        Match match = new Match(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            match.updateScore(1, -2);
        });
    }
}