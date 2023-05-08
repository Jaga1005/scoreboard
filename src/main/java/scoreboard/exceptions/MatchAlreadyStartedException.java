package scoreboard.exceptions;

public class MatchAlreadyStartedException extends RuntimeException {

    public MatchAlreadyStartedException() {
        super("The match for the teams has already started!");
    }
}
