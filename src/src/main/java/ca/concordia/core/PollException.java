package ca.concordia.core;

public class PollException extends Exception {

    public PollException(String message) {
        super("PollException: " + message);
    }
}
