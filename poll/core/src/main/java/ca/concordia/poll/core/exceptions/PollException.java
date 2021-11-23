package ca.concordia.poll.core.exceptions;

public class PollException extends Exception {

    public PollException(String message) {
        super("PollException: " + message);
    }

}
