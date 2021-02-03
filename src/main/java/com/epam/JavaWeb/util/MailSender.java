package com.epam.JavaWeb.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class MailSender {
    private static final Logger logger = LogManager.getLogger(MailSender.class);
    public static final String ENCODING = "UTF-8";
    private static final String MAIL_PROPERTIES = "mail.properties";
    private static final String USER_NAME_PROPERTIES = "mail.user.name";
    private static final String USER_PASSWORD_PROPERTIES = "mail.user.password";
    private final Properties properties;

    public MailSender() {
        properties = new Properties();
        try {
            ClassLoader classLoader = MailSender.class.getClassLoader();
            properties.load(classLoader.getResourceAsStream(MAIL_PROPERTIES));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void sendMessage(String subject, String body, String email) {
        String username = properties.getProperty(USER_NAME_PROPERTIES);
        String password = properties.getProperty(USER_PASSWORD_PROPERTIES);
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject, ENCODING);
            message.setText(body, ENCODING);
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error("Error sending a message", e);
        }
    }
}
