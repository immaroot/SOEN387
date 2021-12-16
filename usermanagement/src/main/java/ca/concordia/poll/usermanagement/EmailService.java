package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.users.AuthenticatedUser;

public interface EmailService {

    boolean sendVerificationEmail(AuthenticatedUser user, Token token, String verificationURL);

}
