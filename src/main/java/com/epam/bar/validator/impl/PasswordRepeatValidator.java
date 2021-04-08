package com.epam.bar.validator.impl;

import com.epam.bar.validator.ChainValidator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

/**
 * Validate password equals
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class PasswordRepeatValidator implements ChainValidator {
    private final String password;
    private final String repeatPassword;
    private ChainValidator validator;

    /**
     * Instantiates a new Password repeat validator.
     *
     * @param password       the password
     * @param repeatPassword the repeat password
     */
    public PasswordRepeatValidator(String password, String repeatPassword) {
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    /**
     * Instantiates a new Password repeat validator.
     *
     * @param password       the password
     * @param repeatPassword the repeat password
     * @param validator      the validator
     */
    public PasswordRepeatValidator(String password, String repeatPassword, ChainValidator validator) {
        this.validator = validator;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (StringUtils.isEmptyOrWhitespaceOnly(repeatPassword) || !password.equals(repeatPassword)) {
            serverMessage = Optional.of("serverMessage.invalid.repeatPassword");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
