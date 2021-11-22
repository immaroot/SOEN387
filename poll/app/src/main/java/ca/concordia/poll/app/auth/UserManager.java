package ca.concordia.poll.app.auth;

import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.exceptions.WrongPasswordException;
import ca.concordia.poll.core.services.AuthenticatedUserRepository;
import ca.concordia.poll.core.services.AuthenticatedUserService;
import ca.concordia.poll.core.services.PasswordSecurity;
import ca.concordia.poll.core.users.AuthenticatedUser;

public class UserManager implements AuthenticatedUserService {

    AuthenticatedUserRepository authenticatedUserRepository;

    public UserManager(UserRepository repository) {
        this.authenticatedUserRepository = repository;
    }

    @Override
    public AuthenticatedUser login(String email, String password) throws UserManagementException {
        AuthenticatedUser user = authenticatedUserRepository.findAuthenticatedUserByEmail(email);
        if (!PasswordSecurity.verify(password, user.getPassword())) {
            throw new WrongPasswordException("Wrong password");
        }
        return user;
    }

    @Override
    public AuthenticatedUser register(AuthenticatedUser newUser) throws UserManagementException {
        return authenticatedUserRepository.saveAuthenticatedUser(newUser);
    }
}
