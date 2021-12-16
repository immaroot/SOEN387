package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.exceptions.WrongPasswordException;
import ca.concordia.poll.core.users.AuthenticatedUser;

public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final TokenRepository tokenRepository;
    final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, TokenRepository tokenRepository, EmailService emailService) {
        this.userRepository  = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService    = emailService;
    }

    @Override
    public AuthenticatedUser login(String email, String password) throws UserManagementException {
        AuthenticatedUser user = userRepository.findAuthenticatedUserByEmail(email);
        if (tokenRepository.isConfirmed(user)) {
            if (user.getPassword().equals(password)) {
                return user;
            }
                throw new WrongPasswordException("Wrong password");
        }
        throw new UserManagementException("Account is not yet verified");
    }

    @Override
    public AuthenticatedUser register(AuthenticatedUser user) throws UserManagementException {
        Token token = tokenRepository.addVerificationTokenForUser(user);
        emailService.sendVerificationEmail(user, token, "https://example.com");
        return userRepository.saveAuthenticatedUser(user);
    }

    @Override
    public void confirm(AuthenticatedUser user, String token) {
        tokenRepository.verify(user, token);
    }

    @Override
    public boolean isConfirmed(AuthenticatedUser user) {
        return tokenRepository.isConfirmed(user);
    }
}
