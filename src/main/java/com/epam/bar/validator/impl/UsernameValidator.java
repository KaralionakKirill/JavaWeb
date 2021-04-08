package com.epam.bar.validator.impl;

import com.epam.bar.validator.ChainValidator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

/**
 * Validate login user
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class UsernameValidator implements ChainValidator {
    private static final String USERNAME_PATTERN = "^[A-Za-z_]{4,20}$";
    private static final Integer MAX_LENGTH = 100;
    private final String username;
    private ChainValidator validator;

    /**
     * Instantiates a new Username validator.
     *
     * @param username the username
     */
    public UsernameValidator(String username) {
        this.username = username;
    }

    /**
     * Instantiates a new Username validator.
     *
     * @param username  the username
     * @param validator the validator
     */
    public UsernameValidator(String username, ChainValidator validator) {
        this.validator = validator;
        this.username = username;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (StringUtils.isEmptyOrWhitespaceOnly(username) || !username.matches(USERNAME_PATTERN) ||
                username.length() > MAX_LENGTH) {
            serverMessage = Optional.of("serverMessage.invalid.username");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
