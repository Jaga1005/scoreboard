package scoreboard.exceptions;

public class MatchDoesntExistException extends RuntimeException {

    public MatchDoesntExistException() {
        super("There is no such pair on the scoreboard");
    }
}
