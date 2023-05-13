package scoreboard;

import java.util.Comparator;

public class MatchComparator implements Comparator<Match> {

    private static final int TOTAL_SCORES_ARE_EQUALS = 0;

    @Override
    public int compare(Match match1, Match match2) {
        int compareTotalScores = compareByTotalScoreDesc(match1, match2);

        if (compareTotalScores == TOTAL_SCORES_ARE_EQUALS) {
            return compareByStartTimeAsc(match1, match2);
        }

        return compareTotalScores;
    }

    private static int compareByTotalScoreDesc(Match match1, Match match2) {
        return Integer.compare(match2.getTotalScore(), match1.getTotalScore());
    }

    private static int compareByStartTimeAsc(Match match1, Match match2) {
        return Long.compare(match1.getStartTime(), match2.getStartTime());
    }
}
