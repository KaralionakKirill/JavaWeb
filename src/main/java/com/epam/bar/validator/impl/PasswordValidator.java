package com.epam.bar.validator.impl;

import com.epam.bar.validator.Validator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

public class PasswordValidator implements Validator {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z_!@#$%^&*]{6,}";
    private final String password;
    private Validator validator = null;

    public PasswordValidator(String password) {
        this.password = password;
    }

    public PasswordValidator(String password, Validator validator) {
        this.validator = validator;
        this.password = password;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (StringUtils.isEmptyOrWhitespaceOnly(password) || !password.matches(PASSWORD_PATTERN)) {
            serverMessage = Optional.of("serverMessage.invalid.password");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
