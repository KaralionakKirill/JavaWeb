package com.epam.bar.validator.impl;

import com.epam.bar.command.RequestContext;
import com.epam.bar.command.RequestParameter;
import com.epam.bar.validator.Validator;
import com.mysql.cj.util.StringUtils;

import java.util.Map;
import java.util.Optional;

public class UsernameValidator implements Validator {
    private static final String USERNAME_PATTERN = "^[A-Za-z_]{4,20}$";
    private final Validator validator;

    public UsernameValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Optional<String> validate(Map<String, String> parameters) {
        Optional<String> serverMessage = Optional.empty();
        String username = parameters.get(RequestParameter.LOGIN);
        if (StringUtils.isEmptyOrWhitespaceOnly(username) || !username.matches(USERNAME_PATTERN)) {
            serverMessage = Optional.of("serverMessage.invalid.username");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate(parameters);
        }
        return serverMessage;
    }
}
