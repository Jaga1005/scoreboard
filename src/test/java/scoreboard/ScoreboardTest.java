package scoreboard;

import org.junit.jupiter.api.Test;
import scoreboard.exceptions.MatchAlreadyStartedException;
import scoreboard.exceptions.NotUniquePairException;
import scoreboard.exceptions.TeamAlreadyInMatchException;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {
    private static final String HOME_TEAM_NAME = "TeamA";
    private static final String HOME_TEAM_NAME_2 = "TeamD";
    private static final String AWAY_TEAM_NAME = "TeamB";
    private static final String AWAY_TEAM_NAME_2 = "TeamC";

    @Test
    void whenCreateNewGame_givenUniquePair_thenAddNewPairToScoreboard() {
        //when
        Scoreboard scoreboard = new Scoreboard();

        //given
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        var expected = new HashMap<String, String>();
        expected.put(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        assertEquals(expected, scoreboard.getScores());
    }

    @Test
    void whenCreateNewGame_givenTwoMatchesWithTheSameHomeTeam_thenAddNewPairToScoreboard() {
        //when
        Scoreboard scoreboard = new Scoreboard();

        //given
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(TeamAlreadyInMatchException.class, () -> {
            //given
            scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME_2);
        });
    }

    @Test
    void whenCreateNewGame_givenTwoMatchesWithTheSameAwayTeam_thenAddNewPairToScoreboard() {
        //when
        Scoreboard scoreboard = new Scoreboard();

        //given
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(TeamAlreadyInMatchException.class, () -> {
            //given
            scoreboard.startNewGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenCreateNewGame_givenNotUniquePair_thenThrowAnError() {
        //when
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchAlreadyStartedException.class, () -> {
            //given
            scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenCreateNewGame_givenNullHomeTeam_thenThrowAnError() {
        //when
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(NullPointerException.class, () -> {
            //given
            scoreboard.startNewGame(null, AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenCreateNewGame_givenNullAwayTeam_thenThrowAnError() {
        //when
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(NullPointerException.class, () -> {
            //given
            scoreboard.startNewGame(HOME_TEAM_NAME, null);
        });
    }

    @Test
    void whenCreateNewGame_givenEmptyHomeTeam_thenThrowAnError() {
        //when
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(NullPointerException.class, () -> {
            //given
            scoreboard.startNewGame("", AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenCreateNewGame_givenEmptyAwayTeam_thenThrowAnError() {
        //when
        Scoreboard scoreboard = new Scoreboard();

        //given
        assertThrows(NullPointerException.class, () -> {
            //given
            scoreboard.startNewGame(HOME_TEAM_NAME, "");
        });
    }

    @Test
    void whenCreateNewGame_givenTheSameTeams_thenThrowAnError() {
        //when
        Scoreboard scoreboard = new Scoreboard();

        assertThrows(NotUniquePairException.class, () -> {
            //given
            scoreboard.startNewGame(HOME_TEAM_NAME, HOME_TEAM_NAME);
        });
    }
}