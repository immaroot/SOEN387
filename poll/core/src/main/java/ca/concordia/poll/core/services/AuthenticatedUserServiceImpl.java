package ca.concordia.poll.core.services;

import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.exceptions.WrongPasswordException;
import ca.concordia.poll.core.users.AuthenticatedUser;

public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {

    private final AuthenticatedUserRepository authenticatedUserRepository;

    public AuthenticatedUserServiceImpl(AuthenticatedUserRepository authenticatedUserRepository) {
        this.authenticatedUserRepository = authenticatedUserRepository;
    }

    @Override
    public AuthenticatedUser login(String email, String password) throws UserManagementException {
        AuthenticatedUser user = authenticatedUserRepository.findAuthenticatedUserByEmail(email);
        if (user.getPassword().equals(password)) {
            return user;
        }
        throw new WrongPasswordException("Wrong password");
    }

    @Override
    public AuthenticatedUser register(AuthenticatedUser user) throws UserManagementException {
        return authenticatedUserRepository.saveAuthenticatedUser(user);
    }
}
