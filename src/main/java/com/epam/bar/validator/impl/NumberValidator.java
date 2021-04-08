package com.epam.bar.validator.impl;

import com.epam.bar.validator.ChainValidator;

import java.util.Optional;

/**
 * Validate number
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class NumberValidator implements ChainValidator {
    private static final Integer MAX_LENGTH = 10;
    private final String number;
    private ChainValidator validator;

    /**
     * Instantiates a new Number validator.
     *
     * @param number the number
     */
    public NumberValidator(String number) {
        this.number = number;
    }

    /**
     * Instantiates a new Number validator.
     *
     * @param number    the number
     * @param validator the validator
     */
    public NumberValidator(String number, ChainValidator validator) {
        this.number = number;
        this.validator = validator;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        try {
            if (number.charAt(0) != '0' && number.length() <= MAX_LENGTH) {
                Integer.parseInt(number);
            } else {
                serverMessage = Optional.of("serverMessage.invalid.number");
            }
        } catch (NumberFormatException e) {
            serverMessage = Optional.of("serverMessage.invalid.number");
        }

        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }

        return serverMessage;
    }
}
