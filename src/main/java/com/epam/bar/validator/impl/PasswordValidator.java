package com.epam.bar.validator.impl;

import com.epam.bar.command.RequestContext;
import com.epam.bar.command.RequestParameter;
import com.epam.bar.validator.Validator;
import com.mysql.cj.util.StringUtils;

import java.util.Map;
import java.util.Optional;

public class PasswordValidator implements Validator {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z_!@#$%^&*]{6,}";
    private final Validator validator;

    public PasswordValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Optional<String> validate(Map<String, String> parameters) {
        Optional<String> serverMessage = Optional.empty();
        String password = parameters.get(RequestParameter.PASSWORD);
        if (StringUtils.isEmptyOrWhitespaceOnly(password) || !password.matches(PASSWORD_PATTERN)) {
            serverMessage = Optional.of("serverMessage.invalid.password");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate(parameters);
        }
        return serverMessage;
    }
}
