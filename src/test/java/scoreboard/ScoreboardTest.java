package scoreboard;

import org.junit.jupiter.api.Test;
import scoreboard.exceptions.MatchAlreadyStartedException;
import scoreboard.exceptions.NotUniquePairException;
import scoreboard.exceptions.MatchDoesntExistException;
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
        var expected = new HashMap<String, Match>();
        expected.put(HOME_TEAM_NAME, Match.newTeam(HOME_TEAM_NAME, AWAY_TEAM_NAME));

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
        assertThrows(IllegalArgumentException.class, () -> {
            //given
            scoreboard.startNewGame(null, AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenCreateNewGame_givenNullAwayTeam_thenThrowAnError() {
        //when
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //given
            scoreboard.startNewGame(HOME_TEAM_NAME, null);
        });
    }

    @Test
    void whenCreateNewGame_givenEmptyHomeTeam_thenThrowAnError() {
        //when
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //given
            scoreboard.startNewGame("", AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenCreateNewGame_givenEmptyAwayTeam_thenThrowAnError() {
        //when
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //given
            scoreboard.startNewGame(HOME_TEAM_NAME, "");
        });
    }

    @Test
    void whenCreateNewGame_givenTheSameTeams_thenThrowAnError() {
        //when
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(NotUniquePairException.class, () -> {
            //given
            scoreboard.startNewGame(HOME_TEAM_NAME, HOME_TEAM_NAME);
        });
    }

    @Test
    void whenUpdateGame_givenExistingMatch_thenUpdateTheGame() {
        //when
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);
        scoreboard.startNewGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2);

        //given
        scoreboard.updateScore(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 2);
        //then
        var expected = new HashMap<String, Match>();
        expected.put(HOME_TEAM_NAME, new Match(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 2));
        expected.put(HOME_TEAM_NAME_2, new Match(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2, 0, 0));


        assertEquals(expected, scoreboard.getScores());
    }

    @Test
    void whenUpdateGame_givenExistingMatchWithZeroValue_thenUpdateTheGame() {
        //when
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //given
        scoreboard.updateScore(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 0);
        //then
        var expected = new HashMap<String, Match>();
        Match match = new Match(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 0);
        expected.put(HOME_TEAM_NAME, match);

        assertEquals(expected, scoreboard.getScores());
    }

    @Test
    void whenUpdateGame_givenWithNotExistingHomeTeamMatch_thenThrowException() {
        //when
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchDoesntExistException.class, () -> {
            //given
            scoreboard.updateScore(HOME_TEAM_NAME_2, AWAY_TEAM_NAME, 1, 2);
        });
    }

    @Test
    void whenUpdateGame_givenWithNotExistingAwayTeamMatch_thenThrowException() {
        //when
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchDoesntExistException.class, () -> {
            //given
            scoreboard.updateScore(HOME_TEAM_NAME, AWAY_TEAM_NAME_2, 1, 2);
        });
    }

    @Test
    void whenUpdateGame_givenNotExistingMatchWithNullHomeTeam_thenThrowException() {
        //when
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchDoesntExistException.class, () -> {
            //given
            scoreboard.updateScore(null, AWAY_TEAM_NAME, 1, 2);
        });
    }

    @Test
    void whenUpdateGame_givenNotExistingMatchWithNullAwayTeam_thenThrowException() {
        //when
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchDoesntExistException.class, () -> {
            //given
            scoreboard.updateScore(HOME_TEAM_NAME, null, 1, 2);
        });
    }

    @Test
    void whenUpdateGame_givenExistingMatchWithNegativeNumberOnHomeTeamScore_thenThrowException() {
        //when
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //given
            scoreboard.updateScore(HOME_TEAM_NAME, AWAY_TEAM_NAME, -1, 2);
        });
    }

    @Test
    void whenUpdateGame_givenExistingMatchWithNegativeNumberOnAwayTeamScore_thenThrowException() {
        //when
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //given
            scoreboard.updateScore(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, -2);
        });
    }
}