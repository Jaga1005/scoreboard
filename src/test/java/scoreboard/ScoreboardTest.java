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
    private static final String HOME_TEAM_NAME = "GONDOR";
    private static final String HOME_TEAM_NAME_LOWER_CASE = "gondor";
    private static final String HOME_TEAM_NAME_2 = "ROHAN";
    private static final String AWAY_TEAM_NAME = "MORDOR";
    private static final String AWAY_TEAM_NAME_LOWER_CASE = "mordor";
    private static final String AWAY_TEAM_NAME_2 = "SHIRE";

    @Test
    void whenStartNewGame_givenUniqueTeams_thenAddNewGameToScoreboard() {
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
    void whenStartNewGame_givenSecondMatchWithTheSameHomeTeamButLowerCase_thenThrowException() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchAlreadyStartedException.class, () -> {
            //when
            scoreboard.startNewGame(HOME_TEAM_NAME_LOWER_CASE, AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenStartNewGame_givenSecondMatchWithTheSameAwayTeamButLowerCase_thenThrowException() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(MatchAlreadyStartedException.class, () -> {
            //when
            scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME_LOWER_CASE);
        });
    }

    @Test
    void whenStartNewGame_givenSecondMatchWithTheSameHomeTeam_thenThrowException() {
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
    void whenStartNewGame_givenSecondMatchWithTheSameAwayTeam_thenThrowException() {
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
    void whenStartNewGame_givenExistingMatch_thenThrowAnException() {
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
    void whenStartNewGame_givenMatchWithNullHomeTeam_thenThrowAnException() {
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            scoreboard.startNewGame(null, AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenStartNewGame_givenMatchWithNullAwayTeam_thenThrowAnException() {
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            scoreboard.startNewGame(HOME_TEAM_NAME, null);
        });
    }

    @Test
    void whenStartNewGame_givenMatchWithEmptyHomeTeam_thenThrowAnException() {
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            scoreboard.startNewGame("", AWAY_TEAM_NAME);
        });
    }

    @Test
    void whenStartNewGame_givenMatchWithEmptyAwayTeam_thenThrowAnException() {
        Scoreboard scoreboard = new Scoreboard();

        //then
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            scoreboard.startNewGame(HOME_TEAM_NAME, "");
        });
    }

    @Test
    void whenStartNewGame_givenMatchWithTheSameTeams_thenThrowAnException() {
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
    void whenUpdateGame_givenExistingMatchHomeTeamWithLowerCase_thenUpdateTheGame() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);
        scoreboard.startNewGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2);

        //when
        scoreboard.updateGame(HOME_TEAM_NAME_LOWER_CASE, AWAY_TEAM_NAME, 1, 2);

        //then
        var expected = new HashMap<String, Match>();
        expected.put(HOME_TEAM_NAME, new Match(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 2));
        expected.put(HOME_TEAM_NAME_2, new Match(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2, 0, 0));

        assertEquals(expected, scoreboard.getScores());
    }
    @Test
    void whenUpdateGame_givenExistingMatchAwayTeamWithLowerCase_thenUpdateTheGame() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);
        scoreboard.startNewGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2);

        //when
        scoreboard.updateGame(HOME_TEAM_NAME, AWAY_TEAM_NAME_LOWER_CASE, 1, 2);

        //then
        var expected = new HashMap<String, Match>();
        expected.put(HOME_TEAM_NAME, new Match(HOME_TEAM_NAME, AWAY_TEAM_NAME, 1, 2));
        expected.put(HOME_TEAM_NAME_2, new Match(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2, 0, 0));

        assertEquals(expected, scoreboard.getScores());
    }

    @Test
    void whenUpdateGame_givenMatchWithNotExistingHomeTeam_thenThrowException() {
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
    void whenUpdateGame_givenMatchWithNotExistingAwayTeam_thenThrowException() {
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
    void whenUpdateGame_givenMatchWithNotExistingNullHomeTeam_thenThrowException() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);

        //then
        assertThrows(IllegalArgumentException.class, () -> {
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
        assertThrows(IllegalArgumentException.class, () -> {
            //when
            scoreboard.updateGame(HOME_TEAM_NAME, null, 1, 2);
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
    void whenFinishGame_givenExistingMatchWithHomeTeamWithLowerCase_thenRemoveGameFromScoreboard() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);
        scoreboard.startNewGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2);

        //when
        scoreboard.finishGame(HOME_TEAM_NAME_LOWER_CASE, AWAY_TEAM_NAME);

        //then
        var expected = new HashMap<String, Match>();
        expected.put(HOME_TEAM_NAME_2, new Match(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2, 0, 0));

        assertEquals(expected, scoreboard.getScores());
    }

    @Test
    void whenFinishGame_givenExistingMatchWithAwayTeamWithLowerCase_thenRemoveGameFromScoreboard() {
        //given
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startNewGame(HOME_TEAM_NAME, AWAY_TEAM_NAME);
        scoreboard.startNewGame(HOME_TEAM_NAME_2, AWAY_TEAM_NAME_2);

        //when
        scoreboard.finishGame(HOME_TEAM_NAME, AWAY_TEAM_NAME_LOWER_CASE);

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
        assertThrows(IllegalArgumentException.class, () -> {
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
        assertThrows(IllegalArgumentException.class, () -> {
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