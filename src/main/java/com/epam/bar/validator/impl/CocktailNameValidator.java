package com.epam.bar.validator.impl;

import com.epam.bar.validator.Validator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

public class CocktailNameValidator implements Validator {
    private final String cocktailName;
    private Validator validator = null;

    public CocktailNameValidator(String cocktailName) {
        this.cocktailName = cocktailName;
    }

    public CocktailNameValidator(String cocktailName, Validator validator) {
        this.validator = validator;
        this.cocktailName = cocktailName;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (StringUtils.isEmptyOrWhitespaceOnly(cocktailName) || cocktailName.length() < 4 ||
                cocktailName.length() > 30 ) {
            serverMessage = Optional.of("serverMessage.invalid.cocktailName");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
