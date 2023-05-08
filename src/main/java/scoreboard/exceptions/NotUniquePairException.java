package scoreboard.exceptions;

public class NotUniquePairException extends RuntimeException {
    public NotUniquePairException() {
        super("HomeTeam and AwayTeam must be different!");
    }
}
