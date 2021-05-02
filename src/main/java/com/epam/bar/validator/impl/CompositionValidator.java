package com.epam.bar.validator.impl;

import com.epam.bar.validator.ChainValidator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

/**
 * Validate cocktail composition
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class CompositionValidator implements ChainValidator {
    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 1000;
    private ChainValidator validator;
    private final String composition;

    /**
     * Instantiates a new Composition validator.
     *
     * @param composition the composition
     */
    public CompositionValidator(String composition) {
        this.composition = composition;
    }

    /**
     * Instantiates a new Composition validator.
     *
     * @param composition the composition
     * @param validator   the validator
     */
    public CompositionValidator(String composition, ChainValidator validator) {
        this.validator = validator;
        this.composition = composition;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (StringUtils.isNullOrEmpty(composition) || composition.length() < MIN_LENGTH || composition.length() > MAX_LENGTH) {
            serverMessage = Optional.of("serverMessage.invalid.composition");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
