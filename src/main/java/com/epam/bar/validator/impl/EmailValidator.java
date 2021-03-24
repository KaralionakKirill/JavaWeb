package com.epam.bar.validator.impl;

import com.epam.bar.validator.Validator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

public class EmailValidator implements Validator {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private final String email;
    private Validator validator = null;

    public EmailValidator(String email) {
        this.email = email;
    }

    public EmailValidator(String email, Validator validator) {
        this.validator = validator;
        this.email = email;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (StringUtils.isEmptyOrWhitespaceOnly(email) || !email.matches(EMAIL_PATTERN)) {
            serverMessage = Optional.of("serverMessage.invalid.email");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
