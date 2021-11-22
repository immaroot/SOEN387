package ca.concordia.poll.core.exceptions;

public class UserManagementException extends Exception {

    public UserManagementException(String message) {
        super("UserManagementException: " + message);
    }
}
