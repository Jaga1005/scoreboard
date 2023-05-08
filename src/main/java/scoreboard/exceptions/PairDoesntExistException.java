package scoreboard.exceptions;

public class PairDoesntExistException extends RuntimeException {

    public PairDoesntExistException(String homeTeam, String awayTeam) {
        super("There is no such pair on the scoreboard: " + homeTeam + " and " + awayTeam);
    }
}
