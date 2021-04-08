package com.epam.bar.validator.impl;

import com.epam.bar.validator.ChainValidator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

/**
 * Validate user password
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class PasswordValidator implements ChainValidator {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z_!@#$%^&*]{6,}";
    private static final Integer MAX_LENGTH = 100;
    private final String password;
    private ChainValidator validator;

    /**
     * Instantiates a new Password validator.
     *
     * @param password the password
     */
    public PasswordValidator(String password) {
        this.password = password;
    }

    /**
     * Instantiates a new Password validator.
     *
     * @param password  the password
     * @param validator the validator
     */
    public PasswordValidator(String password, ChainValidator validator) {
        this.validator = validator;
        this.password = password;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (StringUtils.isEmptyOrWhitespaceOnly(password) || !password.matches(PASSWORD_PATTERN)
                || password.length() > MAX_LENGTH) {
            serverMessage = Optional.of("serverMessage.invalid.password");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
