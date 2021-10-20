package ca.concordia.SOEN387;

public class PollException extends Exception {

    public PollException(String message) {
        super("PollException: " + message);
    }
}
