package ca.concordia.core;

public class ClosedPollException extends PollException {

    public ClosedPollException() {
        super("Poll is closed. You need to create a new poll!");
    }
}
