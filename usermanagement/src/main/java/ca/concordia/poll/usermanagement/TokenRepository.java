package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.users.AuthenticatedUser;

import java.util.UUID;

public interface TokenRepository {

    String getUserVerificationToken(AuthenticatedUser user);

    Token addVerificationTokenForUser(AuthenticatedUser user);

    boolean isConfirmed(AuthenticatedUser user);

    void verify(AuthenticatedUser user, String token);

    static Token generateToken() {
        return new Token(UUID.randomUUID().toString());
    }

}
