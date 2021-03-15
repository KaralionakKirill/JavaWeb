package com.epam.bar.validator.impl;

import com.epam.bar.command.RequestContext;
import com.epam.bar.command.RequestParameter;
import com.epam.bar.validator.Validator;
import com.mysql.cj.util.StringUtils;

import java.util.Map;
import java.util.Optional;

public class EmailValidator implements Validator {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private final Validator validator;

    public EmailValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Optional<String> validate(Map<String, String> parameters) {
        Optional<String> serverMessage = Optional.empty();
        String email = parameters.get(RequestParameter.EMAIL);
        if (StringUtils.isEmptyOrWhitespaceOnly(email) || !email.matches(EMAIL_PATTERN)) {
            serverMessage = Optional.of("serverMessage.invalid.email");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate(parameters);
        }
        return serverMessage;
    }
}
