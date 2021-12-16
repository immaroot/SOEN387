package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.users.AuthenticatedUser;

public interface UserService {

    AuthenticatedUser login(String email, String password) throws UserManagementException;

    AuthenticatedUser register(AuthenticatedUser user) throws UserManagementException;

    void confirm(AuthenticatedUser user, String token);

    boolean isConfirmed(AuthenticatedUser user);

}
