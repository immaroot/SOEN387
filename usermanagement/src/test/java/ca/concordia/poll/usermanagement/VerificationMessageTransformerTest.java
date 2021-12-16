package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.users.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VerificationMessageTransformerTest {

    AuthenticatedUser user;
    String token = "TOKEN";
    String verificationURL = "https://example.com";

    @BeforeEach
    void setUp() {
        user = new AuthenticatedUser();
        user.setUserID(1);
        user.setEmail("test@example.com");
        user.setPassword("secret");
        user.setFullName("Bob");
    }

    @Test
    void processMessage() {
        String result = VerificationMessageTransformer.processMessage(user, token, verificationURL);
        String expected = "<html>" +
                "<body>" +
                "<p>" +
                "Hello there " + user.getFullName() +
                ", please click link to verify your email: " +
                "<a href=\"https://example.com/TOKEN\" > verify </a>" +
                "</p>" +
                "</body>" +
                "</html>";

        assertEquals(expected, result);
    }
}