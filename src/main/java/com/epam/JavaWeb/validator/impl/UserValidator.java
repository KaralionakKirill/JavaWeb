package com.epam.JavaWeb.validator.impl;

import com.epam.JavaWeb.constant.ValidatorConstant;
import com.epam.JavaWeb.validator.Validator;

public class UserValidator implements Validator {
    @Override
    public boolean isValid(String... line) {
        return (line[0].matches(ValidatorConstant.EMAIL_REGEX) && line[1].matches(ValidatorConstant.PASSWORD_REGEX));
    }
}
