package com.epam.bar.validator.impl;

import com.epam.bar.validator.Validator;

import java.util.Optional;

public class RateValidator implements Validator {
    private Validator validator = null;
    private final int rate;

    public RateValidator(int rate) {
        this.rate = rate;
    }

    public RateValidator(int rate, Validator validator) {
        this.validator = validator;
        this.rate = rate;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (rate < 0 || rate > 5) {
            serverMessage = Optional.of("serverMessage.invalid.rate");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
