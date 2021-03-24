package com.epam.bar.validator.impl;

import com.epam.bar.validator.Validator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

public class UsernameValidator implements Validator {
    private static final String USERNAME_PATTERN = "^[A-Za-z_]{4,20}$";
    private final String username;
    private Validator validator = null;

    public UsernameValidator(String username) {
        this.username = username;
    }

    public UsernameValidator(String username, Validator validator) {
        this.validator = validator;
        this.username = username;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (StringUtils.isEmptyOrWhitespaceOnly(username) || !username.matches(USERNAME_PATTERN)) {
            serverMessage = Optional.of("serverMessage.invalid.username");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
