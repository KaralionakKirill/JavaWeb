package com.epam.bar.validator.impl;

import com.epam.bar.validator.ChainValidator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

/**
 * Validate cocktail feedback
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class FeedbackValidator implements ChainValidator {
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 500;
    private final String feedback;
    private ChainValidator validator;

    /**
     * Instantiates a new Feedback validator.
     *
     * @param feedback the feedback
     */
    public FeedbackValidator(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Instantiates a new Feedback validator.
     *
     * @param feedback  the feedback
     * @param validator the validator
     */
    public FeedbackValidator(String feedback, ChainValidator validator) {
        this.validator = validator;
        this.feedback = feedback;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (StringUtils.isNullOrEmpty(feedback) || feedback.length() < MIN_LENGTH || feedback.length() > MAX_LENGTH) {
            serverMessage = Optional.of("serverMessage.invalid.feedback");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
