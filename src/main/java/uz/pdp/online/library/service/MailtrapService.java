package uz.pdp.online.library.service;

import lombok.NonNull;
import uz.pdp.online.library.dao.AuthUserDAO;
import uz.pdp.online.library.entity.AuthUser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailtrapService {
    private static MailtrapService mailtrapService;
    private static final AuthUserDAO authUserDAO = new AuthUserDAO();
    private static final String username = "shahobiddinatamurodov@gmail.com";  // Gmail manzilingiz
    private static final String password = "tkci podc qimn xtfz"; // Gmail App Password

    public static MailtrapService getMailtrapService() {
        if (mailtrapService == null) {
            mailtrapService = new MailtrapService();
        }
        return mailtrapService;
    }

    public static void sendActivationEmail(@NonNull String userID) {
        AuthUser authUser = authUserDAO.findById(userID);

        try {
            var properties = getProperties();
            var session = getSession(properties);
            var message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(authUser.getEmail()));
            message.setSubject("Account Activation");

            var multipart = new MimeMultipart();
            var contentMessage = new MimeBodyPart();

            String body = """
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>Activation Page</title>
                        <style>
                            body {
                                background-color: #ff5722;
                                color: white;
                            }
                            h1 {
                                font-size: 24px;
                            }
                        </style>
                    </head>
                    <body>
                        <h1>Welcome To Our Service!</h1>
                        <h2>To activate your account, click the link below:</h2>
                        <div>
                            <a href="http://localhost:8080/activation?token=%s" target="_blank">Activate Now</a>
                        </div>
                    </body>
                    </html>
                    """.formatted(userID);
            contentMessage.setContent(body, "text/html");
            multipart.addBodyPart(contentMessage);
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Activation email sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587"); // STARTTLS port
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }

    private static Session getSession(Properties properties) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}
