package ca.concordia.poll.app.auth;

import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.services.AuthenticatedUserRepository;
import ca.concordia.poll.core.services.PasswordSecurity;
import ca.concordia.poll.core.users.AuthenticatedUser;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements AuthenticatedUserRepository {

    List<AuthenticatedUser> users;

    public UserRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public AuthenticatedUser saveAuthenticatedUser(AuthenticatedUser newUser) throws UserManagementException {
        if (users.stream().anyMatch(user -> user.getUserID() == newUser.getUserID())) {
            throw new UserManagementException("The ID already exists.");
        }
        String hashedPassword = PasswordSecurity.getHash(newUser.getPassword());
        newUser.setPassword(hashedPassword);
        users.add(newUser);
        return newUser;
    }

    @Override
    public AuthenticatedUser findAuthenticatedUserByEmail(String email) throws UserManagementException {
        AuthenticatedUser returnedUser = users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
        if (returnedUser == null) {
            throw new UserManagementException("No user found.");
        }
        return returnedUser;
    }
}
