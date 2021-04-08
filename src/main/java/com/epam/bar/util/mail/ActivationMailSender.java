package com.epam.bar.util.mail;

import com.epam.bar.util.AppConfig;

/**
 * Sends an email with activation link to the given address
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class ActivationMailSender extends MailSender {
    private static final String SUBJECT = "BarmanHelper account verification";
    private static final String TEXT = "Follow the link to verify your account:";
    private static final String VERIFICATION_LINK = "http://%s:%s/controller?command=verification&verification_code=%s";

    private final String verificationCode;

    /**
     * Instantiates a new Activation mail sender.
     *
     * @param verificationCode the verification code
     * @param email            the email
     */
    public ActivationMailSender(String verificationCode, String email) {
        super(email);
        this.verificationCode = verificationCode;
    }

    @Override
    public void run() {
        AppConfig appConfig = AppConfig.getInstance();
        sendMessage(SUBJECT, TEXT + "\n" + String.format(VERIFICATION_LINK,
                appConfig.getServerHost(), appConfig.getServerPort(), verificationCode));
    }
}
