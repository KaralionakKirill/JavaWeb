package com.epam.bar.validator.impl;

import com.epam.bar.validator.ChainValidator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

/**
 * Validate user email
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class EmailValidator implements ChainValidator {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final Integer MAX_LENGTH = 100;
    private final String email;
    private ChainValidator validator;

    /**
     * Instantiates a new Email validator.
     *
     * @param email the email
     */
    public EmailValidator(String email) {
        this.email = email;
    }

    /**
     * Instantiates a new Email validator.
     *
     * @param email     the email
     * @param validator the validator
     */
    public EmailValidator(String email, ChainValidator validator) {
        this.validator = validator;
        this.email = email;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (StringUtils.isEmptyOrWhitespaceOnly(email) || !email.matches(EMAIL_PATTERN)
                || email.length() > MAX_LENGTH) {
            serverMessage = Optional.of("serverMessage.invalid.email");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
