package scoreboard.exceptions;

public class TeamAlreadyInMatchException extends RuntimeException {

    public TeamAlreadyInMatchException() {
        super("Team has already started a match!");
    }
}
