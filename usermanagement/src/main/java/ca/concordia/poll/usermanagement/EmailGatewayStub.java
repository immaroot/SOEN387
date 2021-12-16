package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.users.AuthenticatedUser;

public class EmailGatewayStub extends EmailGateway {

    public EmailGatewayStub(String host, int port, String username, String password) {
        super(host, port, username, password);
    }

    @Override
    public boolean sendVerificationEmail(AuthenticatedUser user, Token token, String verificationURL) {
        return true;
    }
}
