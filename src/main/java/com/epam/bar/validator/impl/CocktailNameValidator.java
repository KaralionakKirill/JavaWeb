package com.epam.bar.validator.impl;

import com.epam.bar.command.RequestContext;
import com.epam.bar.command.RequestParameter;
import com.epam.bar.validator.Validator;
import com.mysql.cj.util.StringUtils;

import java.util.Map;
import java.util.Optional;

public class CocktailNameValidator implements Validator {
    private static final String COCKTAIL_NAME_PATTERN = "^[A-Za-z_]{4,20}$";
    private final Validator validator;

    public CocktailNameValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Optional<String> validate(Map<String, String> parameters) {
        Optional<String> serverMessage = Optional.empty();
        String cocktailName = parameters.get(RequestParameter.COCKTAIL_NAME);
        if (StringUtils.isEmptyOrWhitespaceOnly(cocktailName) || !cocktailName.matches(COCKTAIL_NAME_PATTERN)) {
            serverMessage = Optional.of("serverMessage.invalid.cocktailName");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate(parameters);
        }
        return serverMessage;
    }
}
