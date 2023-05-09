package scoreboard;

import org.junit.jupiter.api.Test;
import scoreboard.exceptions.MatchAlreadyStartedException;
import scoreboard.exceptions.MatchDoesntExistException;
import scoreboard.exceptions.NotUniquePairException;
import scoreboard.exceptions.TeamAlreadyInMatchException;

import java.util.ArrayList;
import java.util.HashMap;

import static java.util.Collections.EMPTY_LIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void whenCreateNewGame_givenSecondMatchWithTheSameHomeTeam_thenAddNewPairToScoreboard() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(TeamAlreadyInMatchException.class, () -> {
            //when
            scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME_2);
        });
    }

    @Test
    void whenCreateNewGame_givenSecondMatchWithTheSameAwayTeam_thenAddNewPairToScoreboard() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(TeamAlreadyInMatchException.class, () -> {
            //when
            scoreboard.startNewGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenCreateNewGame_givenNotUniquePair_thenThrowAnError() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchAlreadyStartedException.class, () -> {
            //when
            scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenCreateNewGame_givenNullHomeTeam_thenThrowAnError() {
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            scoreboard.startNewGame(null, AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenCreateNewGame_givenNullAwayTeam_thenThrowAnError() {
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            scoreboard.startNewGame(HOME_TEAM_NAME, null);
        });
    }

    @Test
    void whenCreateNewGame_givenEmptyHomeTeam_thenThrowAnError() {
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            scoreboard.startNewGame("", AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenCreateNewGame_givenEmptyAwayTeam_thenThrowAnError() {
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            scoreboard.startNewGame(HOME_TEAM_NAME, "");
        });
    }

    @Test
    void whenCreateNewGame_givenTheSameTeams_thenThrowAnError() {
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(NotUniquePairException.class, () -> {
            //when
            scoreboard.startNewGame(HOME_TEAM_NAME, HOME_TEAM_NAME);
        });
    }

    @Test
    void whenUpdateGame_givenExistingMatch_thenUpdateTheGame() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);
        scoreboard.startNewGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2);

        //when
        scoreboard.updateGame(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 2);

        //then
        var expected = new HashMap<String, Match>();
        expected.put(HOME_TEAM_NAME, new Match(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 2));
        expected.put(HOME_TEAM_NAME_2, new Match(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2, 0, 0));

        assertEquals(expected, scoreboard.getScores());
    }

    @Test
    void whenUpdateGame_givenExistingMatchWithZeroValue_thenUpdateTheGame() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //when
        scoreboard.updateGame(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 0);

        //then
        var expected = new HashMap<String, Match>();
        expected.put(HOME_TEAM_NAME, new Match(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 0));

        assertEquals(expected, scoreboard.getScores());
    }

    @Test
    void whenUpdateGame_givenWithNotExistingHomeTeamMatch_thenThrowException() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchDoesntExistException.class, () -> {
            //when
            scoreboard.updateGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME, 1, 2);
        });
    }

    @Test
    void whenUpdateGame_givenWithNotExistingAwayTeamMatch_thenThrowException() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchDoesntExistException.class, () -> {
            //when
            scoreboard.updateGame(HOME_TEAM_NAME, AWAY_TEAM_NAME_2, 1, 2);
        });
    }

    @Test
    void whenUpdateGame_givenNotExistingMatchWithNullHomeTeam_thenThrowException() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchDoesntExistException.class, () -> {
            //when
            scoreboard.updateGame(null, AWAY_TEAM_NAME, 1, 2);
        });
    }

    @Test
    void whenUpdateGame_givenNotExistingMatchWithNullAwayTeam_thenThrowException() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchDoesntExistException.class, () -> {
            //when
            scoreboard.updateGame(HOME_TEAM_NAME, null, 1, 2);
        });
    }

    @Test
    void whenUpdateGame_givenExistingMatchWithNegativeNumberOnHomeTeamScore_thenThrowException() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            scoreboard.updateGame(HOME_TEAM_NAME, AWAY_TEAM_NAME, -1, 2);
        });
    }

    @Test
    void whenUpdateGame_givenExistingMatchWithNegativeNumberOnAwayTeamScore_thenThrowException() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            scoreboard.updateGame(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, -2);
        });
    }

    @Test
    void whenFinishGame_givenExistingMatch_thenRemoveGameFromScoreboard() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);
        scoreboard.startNewGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2);

        //when
        scoreboard.finishGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        var expected = new HashMap<String, Match>();
        expected.put(HOME_TEAM_NAME_2, new Match(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2, 0, 0));

        assertEquals(expected, scoreboard.getScores());
    }

    @Test
    void whenFinishGame_givenNotExistingMatchWithWrongHomeTeam_thenThrowException() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchDoesntExistException.class, () -> {
            //when
            scoreboard.finishGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenFinishGame_givenNotExistingMatchWithWrongAwayTeam_thenThrowException() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchDoesntExistException.class, () -> {
            //when
            scoreboard.finishGame(HOME_TEAM_NAME, AWAY_TEAM_NAME_2);
        });
    }

    @Test
    void whenFinishGame_givenNotExistingMatchWithNullHomeTeam_thenThrowException() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchDoesntExistException.class, () -> {
            //when
            scoreboard.finishGame(null, AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenFinishGame_givenNotExistingMatchWithNullAwayTeam_thenThrowException() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchDoesntExistException.class, () -> {
            //when
            scoreboard.finishGame(HOME_TEAM_NAME, null);
        });
    }

    @Test
    void whenGetSummary_givenEmptyScoreboard_thenReturnEmptyList() {
        //given
        Scoreboard scoreboard = new Scoreboard();

        //when
        var actualList = scoreboard.getSummary();

        //then
        assertEquals(EMPTY_LIST, actualList);
    }

    @Test
    void whenGetSummary_givenScoreboardWithDifferentTotalScore_thenReturnListSortedByTotalScore() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);
        scoreboard.updateGame(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 2);
        scoreboard.startNewGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2);
        scoreboard.updateGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2, 3, 2);

        //when
        var actualList = scoreboard.getSummary();

        //then
        var expectedList = new ArrayList<Match>();
        expectedList.add(new Match(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2, 3, 2));
        expectedList.add(new Match(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 2));
        assertEquals(expectedList, actualList);
    }

    @Test
    void whenGetSummary_givenScoreboardWithTheSameTotalScore_thenReturnListSortedByTimestamp() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);
        scoreboard.updateGame(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 2);
        scoreboard.startNewGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2);
        scoreboard.updateGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2, 1, 2);

        //when
        var actualList = scoreboard.getSummary();

        //then
        var expectedList = new ArrayList<Match>();
        expectedList.add(new Match(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 2));
        expectedList.add(new Match(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2, 1, 2));
        assertEquals(expectedList, actualList);
    }
}