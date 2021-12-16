package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.users.AuthenticatedUser;

import java.util.Hashtable;

public class TokenRepositoryImpl implements TokenRepository {

    Hashtable<AuthenticatedUser, Token> userTokens;

    public TokenRepositoryImpl() {
        userTokens = new Hashtable<>();
    }

    @Override
    public String getUserVerificationToken(AuthenticatedUser user) {
        return userTokens.get(user).getToken();
    }

    @Override
    public Token addVerificationTokenForUser(AuthenticatedUser user) {
        return userTokens.put(user, TokenRepository.generateToken());
    }

    @Override
    public boolean isConfirmed(AuthenticatedUser user) {
        return userTokens.get(user).isVerified();
    }

    @Override
    public void verify(AuthenticatedUser user, String token) {
        if (userTokens.get(user).getToken().equals(token))
            userTokens.get(user).setVerified(true);
    }
}
