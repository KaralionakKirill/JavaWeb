package com.epam.bar.validator.impl;

import com.epam.bar.validator.Validator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

public class PasswordRepeatValidator implements Validator {
    private final String password;
    private final String repeatPassword;
    private Validator validator = null;

    public PasswordRepeatValidator(String password, String repeatPassword) {
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    public PasswordRepeatValidator(String password, String repeatPassword, Validator validator) {
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
