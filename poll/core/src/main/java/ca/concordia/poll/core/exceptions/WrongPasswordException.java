package ca.concordia.poll.core.exceptions;

public class WrongPasswordException extends UserManagementException {

    public WrongPasswordException(String message) {
        super(message);
    }
}
