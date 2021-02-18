package com.epam.bar.util;

import lombok.extern.log4j.Log4j2;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

@Log4j2
public abstract class MailSender implements Runnable{
    public static final String ENCODING = "UTF-8";
    private static final String MAIL_PROPERTIES = "mail.properties";
    private static final String USER_NAME_PROPERTIES = "mail.user.name";
    private static final String USER_PASSWORD_PROPERTIES = "mail.user.password";
    private final Properties properties;
    private final String email;

    public MailSender(String email) {
        this.email = email;
        properties = new Properties();
        try {
            ClassLoader classLoader = MailSender.class.getClassLoader();
            properties.load(classLoader.getResourceAsStream(MAIL_PROPERTIES));
        } catch (IOException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String subject, String body) {
        String username = properties.getProperty(USER_NAME_PROPERTIES);
        String password = properties.getProperty(USER_PASSWORD_PROPERTIES);
        try {
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject, ENCODING);
            message.setText(body, ENCODING);
            Transport transport = session.getTransport();
            transport.connect(username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            log.debug("Email was successfully sent");
        } catch (MessagingException e) {
            log.error("Error sending a message", e);
        }
    }
}