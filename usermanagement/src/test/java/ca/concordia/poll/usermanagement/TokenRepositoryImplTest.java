package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.users.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenRepositoryImplTest {

    AuthenticatedUser user;
    TokenRepository repository;

    @BeforeEach
    void setUp() {
        user = new AuthenticatedUser();
        user.setUserID(1);
        user.setEmail("test@example.com");
        user.setPassword("secret");
        user.setFullName("Bob");

        repository = new TokenRepositoryImpl();
    }

    @Test
    void addVerificationTokenForUserTest() {
        repository.addVerificationTokenForUser(user);

        assertFalse(repository.isConfirmed(user));

        String token = repository.getUserVerificationToken(user);

        repository.verify(user, token);

        assertTrue(repository.isConfirmed(user));

    }
}