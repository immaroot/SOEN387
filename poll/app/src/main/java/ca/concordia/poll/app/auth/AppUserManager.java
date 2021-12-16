package ca.concordia.poll.app.auth;

import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.exceptions.WrongPasswordException;
import ca.concordia.poll.core.users.AuthenticatedUser;
import ca.concordia.poll.usermanagement.*;

public class AppUserManager implements UserService {

    AppUserRepository userRepository;
    AppTokenRepository tokenRepository;
    EmailService emailService;
    String appURL;

    public AppUserManager(String host, int port, String username, String password) {
        this.userRepository = new AppUserRepository();
        this.tokenRepository = new AppTokenRepository();
        this.emailService = new EmailGateway(host, port, username, password);
    }

    public void setAppURL(String url) {
        this.appURL = url;
    }

    @Override
    public AuthenticatedUser login(String email, String password) throws UserManagementException {
        String hashedPassword = PasswordSecurity.getHash(password);

        AuthenticatedUser user = userRepository.findAuthenticatedUserByEmail(email);
        if (tokenRepository.isConfirmed(user)) {
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
            throw new WrongPasswordException("Wrong password");
        }
        throw new UserManagementException("Account is not yet verified");
    }

    @Override
    public AuthenticatedUser register(AuthenticatedUser user) throws UserManagementException {
        if (appURL == null) {
            throw new UserManagementException("The user management is not configured properly");
        }
        AuthenticatedUser savedUser = userRepository.saveAuthenticatedUser(user);
        Token token = tokenRepository.addVerificationTokenForUser(savedUser);
        emailService.sendVerificationEmail(savedUser, token, appURL);
        return savedUser;
    }

    @Override
    public void confirm(AuthenticatedUser user, String token) {
        tokenRepository.verify(user, token);
    }

    @Override
    public boolean isConfirmed(AuthenticatedUser user) {
        return tokenRepository.isConfirmed(user);
    }

//    public static void main(String[] args) {
//        AuthenticatedUser user = new AuthenticatedUser();
//
//        user.setEmail("test@gmail.com");
//        user.setFullName("paul");
//        user.setPassword("secret");
//
//        AppUserManager userManager = new AppUserManager();
//
//        userManager.setAppURL("example.com");
//
//        try {
//            userManager.register(user);
//        } catch (UserManagementException e) {
//            e.printStackTrace();
//            System.out.println("Did not work");
//        }
//    }
}
