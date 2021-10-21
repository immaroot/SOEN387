package ca.concordia.SOEN387.exceptions;

public class ClosedPollException extends PollException {

    public ClosedPollException() {
        super("Poll is closed. You need to create a new poll!");
    }
}
