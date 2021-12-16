package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.users.AuthenticatedUser;

public class VerificationMessageTransformer {

    public static String processMessage(AuthenticatedUser user, String verificationToken, String verificationURL) {

        return "<html>" +
                "<body>" +
                "<p>" +
                "Hello there " + user.getFullName() +
                ", please click link to verify your email: " +
                "<a href=\"" + verificationURL + "/" + verificationToken + "\" > verify </a>" +
                "</p>" +
                "</body>" +
                "</html>";

    }
}
