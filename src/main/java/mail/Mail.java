package mail;

import data.entities.User;
import data.entities.VerificationToken;
import utils.Constants;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static javax.mail.Message.RecipientType;

public class Mail {

    private String emailPassword;
    private Session session;

    public Mail() {
        // load the email password from the config file
        try (InputStream inputStream = Thread.currentThread()
                .getContextClassLoader().getResourceAsStream(Constants.CONFIG_FILE)) {
            Properties configProperties = new Properties();
            configProperties.load(inputStream);
            emailPassword = configProperties.getProperty("email_password");

            // setup properties
            Properties sessionProperties = System.getProperties();
            sessionProperties.put("mail.smtp.port", "587");
            sessionProperties.put("mail.smtp.auth", "true");
            sessionProperties.put("mail.smtp.starttls.enable", "true");

            // setup mail session
            session = Session.getDefaultInstance(sessionProperties, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendConfirmation(User user, VerificationToken verificationToken) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipients(RecipientType.TO, String.valueOf(new InternetAddress(user.getEmail())));
            message.setSubject("Welcome to Dr. Booze");
            String mailBody =
                    "<h1>Welcome to Dr. Booze</h1><br>" +
                            "<a href='" + Constants.EMAIL_URI + "/auth/verify/" + verificationToken.getToken()
                            + "'>Confirm your email</a>";
            transport(message, mailBody);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    public void resetPasswordConfirmation(User user, int pin) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipients(RecipientType.TO, String.valueOf(new InternetAddress(user.getEmail())));
            message.setSubject("Reset your password");
            String mailBody =
                    "<h1>Your pin to reset the password</h1><br>" + "<p>The pin is " + pin + "</p>";
            transport(message, mailBody);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    private void transport(Message message, String mailBody) throws MessagingException {
        message.setContent(mailBody, "text/html");
        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", "dr.boozeteam@gmail.com", emailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
