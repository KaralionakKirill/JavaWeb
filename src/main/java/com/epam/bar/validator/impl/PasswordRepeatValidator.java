package com.epam.bar.validator.impl;

import com.epam.bar.command.RequestContext;
import com.epam.bar.command.RequestParameter;
import com.epam.bar.validator.Validator;
import com.mysql.cj.util.StringUtils;

import java.util.Map;
import java.util.Optional;

public class PasswordRepeatValidator implements Validator {
    private final Validator validator;

    public PasswordRepeatValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Optional<String> validate(Map<String, String> parameters) {
        Optional<String> serverMessage = Optional.empty();
        String password = parameters.get(RequestParameter.PASSWORD);
        String repeatPassword = parameters.get(RequestParameter.REPEAT_PASSWORD);
        if (StringUtils.isEmptyOrWhitespaceOnly(repeatPassword) || !password.equals(repeatPassword)) {
            serverMessage = Optional.of("serverMessage.invalid.repeatPassword");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate(parameters);
        }
        return serverMessage;
    }
}
