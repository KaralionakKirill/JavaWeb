package com.epam.bar.validator.impl;

import com.epam.bar.validator.ChainValidator;

import java.util.Optional;

/**
 * Validate review rate
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class RateValidator implements ChainValidator {
    private static final int MIN_RATE = 0;
    private static final int MAX_RATE = 5;
    private ChainValidator validator;
    private final int rate;

    /**
     * Instantiates a new Rate validator.
     *
     * @param rate the rate
     */
    public RateValidator(int rate) {
        this.rate = rate;
    }

    /**
     * Instantiates a new Rate validator.
     *
     * @param rate      the rate
     * @param validator the validator
     */
    public RateValidator(int rate, ChainValidator validator) {
        this.validator = validator;
        this.rate = rate;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (rate < MIN_RATE || rate > MAX_RATE) {
            serverMessage = Optional.of("serverMessage.invalid.rate");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
