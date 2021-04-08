package com.epam.bar.validator.impl;

import com.epam.bar.validator.ChainValidator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

/**
 * Validate cocktail name
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class CocktailNameValidator implements ChainValidator {
    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 30;
    private final String cocktailName;
    private ChainValidator validator;

    /**
     * Instantiates a new Cocktail name validator.
     *
     * @param cocktailName the cocktail name
     */
    public CocktailNameValidator(String cocktailName) {
        this.cocktailName = cocktailName;
    }

    /**
     * Instantiates a new Cocktail name validator.
     *
     * @param cocktailName the cocktail name
     * @param validator    the validator
     */
    public CocktailNameValidator(String cocktailName, ChainValidator validator) {
        this.validator = validator;
        this.cocktailName = cocktailName;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (StringUtils.isEmptyOrWhitespaceOnly(cocktailName) || cocktailName.length() < MIN_LENGTH ||
                cocktailName.length() > MAX_LENGTH) {
            serverMessage = Optional.of("serverMessage.invalid.cocktailName");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
