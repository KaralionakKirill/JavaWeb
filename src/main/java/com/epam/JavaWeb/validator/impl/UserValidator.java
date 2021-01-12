package com.epam.JavaWeb.validator.impl;

import com.epam.JavaWeb.validator.Validator;

public class UserValidator implements Validator {
    private static final String EMAIL_REGEX = "([a-z\\d_-]+\\.)*[a-z\\d_-]+@[a-z\\d_-]+(\\.[a-z\\d_-]+)*\\.[a-z]{2,6}";
    private static final String PASSWORD_REGEX = "[a-zA-Z\\d][a-zA-Z\\d_]{3,16}";
    private static final UserValidator instance = new UserValidator();

    private UserValidator() {
    }

    public UserValidator getInstance(){
        return instance;
    }

    @Override
    public boolean isValid(String... line) {
        return (line[0].matches(EMAIL_REGEX) && line[1].matches(PASSWORD_REGEX));
    }
}
