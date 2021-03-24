package com.epam.bar.validator.impl;

import com.epam.bar.validator.Validator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

public class CompositionValidator implements Validator {
    private Validator validator = null;
    private final String composition;

    public CompositionValidator(String composition) {
        this.composition = composition;
    }

    public CompositionValidator(String composition,Validator validator) {
        this.validator = validator;
        this.composition = composition;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (StringUtils.isNullOrEmpty(composition) || composition.length() < 10 || composition.length() > 500) {
            serverMessage = Optional.of("serverMessage.invalid.composition");
        }
        if(validator != null && serverMessage.isEmpty()){
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
