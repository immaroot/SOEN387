package ca.concordia.poll.core.services;

import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.users.AuthenticatedUser;

public interface AuthenticatedUserService {

    AuthenticatedUser login(String email, String password) throws UserManagementException;

    AuthenticatedUser register(AuthenticatedUser user) throws UserManagementException;
}
