package scoreboard.exceptions;

public class PairDoesntExistException extends RuntimeException {

    public PairDoesntExistException() {
        super("There is no such pair on the scoreboard");
    }
}
