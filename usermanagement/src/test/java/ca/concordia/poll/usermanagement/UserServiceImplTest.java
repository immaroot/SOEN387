package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.exceptions.UserManagementException;
import ca.concordia.poll.core.users.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    UserRepository userRepository;
    TokenRepository tokenRepository;
    EmailService emailService;

    UserService userService;

    AuthenticatedUser user;

    @BeforeEach
    void setUp() {

        user = new AuthenticatedUser();
        user.setUserID(1);
        user.setEmail("test@example.com");
        user.setPassword("secret");
        user.setFullName("Bob");

        userRepository = new UserRepository() {

            AuthenticatedUser user;

            @Override
            public AuthenticatedUser saveAuthenticatedUser(AuthenticatedUser newUser) throws UserManagementException {
                user = newUser;
                return newUser;
            }

            @Override
            public AuthenticatedUser findAuthenticatedUserByEmail(String email) throws UserManagementException {
                return user;
            }
        };

        tokenRepository = new TokenRepositoryImpl();
        emailService = new EmailGatewayStub("test", 0, "test", "test");

        userService = new UserServiceImpl(userRepository, tokenRepository, emailService);
    }

    @Test
    void registerTest() throws UserManagementException {
        AuthenticatedUser newUser = userService.register(user);

        assertEquals(user, newUser);
        assertFalse(userService.isConfirmed(user));

        String token = tokenRepository.getUserVerificationToken(user);
        tokenRepository.verify(user, token);

        assertTrue(tokenRepository.isConfirmed(user));
    }

    @Test
    void loginTest() throws UserManagementException {
        userService.register(user);

        String token = tokenRepository.getUserVerificationToken(user);
        userService.confirm(user, token);

        assertEquals(user, userService.login(user.getEmail(), user.getPassword()));
    }
}