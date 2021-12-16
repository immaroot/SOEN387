package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.users.AuthenticatedUser;

public class EmailGateway implements EmailService {

    EmailSender sender;

    public EmailGateway(String host, int port, String username, String password) {
        sender = new EmailSenderImpl(host, port, username, password);
    }

    @Override
    public boolean sendVerificationEmail(AuthenticatedUser user, Token token, String verificationURL) {
        String message = VerificationMessageTransformer.processMessage(user, token.getToken(), verificationURL);
        return sender.sendMail(user, message);
    }
}
