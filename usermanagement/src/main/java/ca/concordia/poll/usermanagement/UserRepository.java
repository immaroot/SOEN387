package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.users.AuthenticatedUser;

public interface UserRepository {

    AuthenticatedUser saveAuthenticatedUser(AuthenticatedUser newUser) throws UserManagementException;

    AuthenticatedUser findAuthenticatedUserByEmail(String email) throws UserManagementException;
}
