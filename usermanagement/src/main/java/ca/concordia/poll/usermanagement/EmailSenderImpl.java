package ca.concordia.poll.usermanagement;

import ca.concordia.poll.core.users.AuthenticatedUser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailSenderImpl implements EmailSender {

    private final String username;
    private final String password;

    private final Properties prop;

    public EmailSenderImpl(String host, int port, String username, String password) {
        prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);

        this.username = username;
        this.password = password;
    }

    public boolean sendMail(AuthenticatedUser user, String msg) {

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
            message.setSubject("Verify your account for SuperPoll");
            message.setContent(msg, "text/html");

            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            e.printStackTrace();

            return false;
        }
    }

}
