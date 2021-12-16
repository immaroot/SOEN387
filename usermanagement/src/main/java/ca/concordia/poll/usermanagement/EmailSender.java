package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.users.AuthenticatedUser;

public interface EmailSender {

    boolean sendMail(AuthenticatedUser user, String msg);
}
