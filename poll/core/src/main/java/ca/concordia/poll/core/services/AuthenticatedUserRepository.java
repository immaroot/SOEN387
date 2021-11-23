package ca.concordia.poll.core.services;

import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.users.AuthenticatedUser;

public interface AuthenticatedUserRepository {

    AuthenticatedUser saveAuthenticatedUser(AuthenticatedUser newUser) throws UserManagementException;

    AuthenticatedUser findAuthenticatedUserByEmail(String email) throws UserManagementException;
}
