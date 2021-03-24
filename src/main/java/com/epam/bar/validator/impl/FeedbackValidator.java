package com.epam.bar.validator.impl;

import com.epam.bar.validator.Validator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

public class FeedbackValidator implements Validator {
    private Validator validator = null;
    private String feedback;

    public FeedbackValidator(String feedback) {
        this.feedback = feedback;
    }

    public FeedbackValidator(String feedback, Validator validator) {
        this.validator = validator;
        this.feedback = feedback;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (StringUtils.isNullOrEmpty(feedback) || feedback.length() < 2 || feedback.length() > 500) {
            serverMessage = Optional.of("serverMessage.invalid.feedback");
        }
        if(validator != null && serverMessage.isEmpty()){
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
