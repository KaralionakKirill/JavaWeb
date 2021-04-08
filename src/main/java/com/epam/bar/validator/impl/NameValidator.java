package com.epam.bar.validator.impl;

import com.epam.bar.validator.ChainValidator;
import com.mysql.cj.util.StringUtils;

import java.util.Optional;

/**
 * Validate first and last user name
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class NameValidator implements ChainValidator {
    private static final String NAME_PATTERN = "^[A-Za-zА-Яа-яЁё']{2,20}?$";
    private static final Integer MAX_LENGTH = 40;
    private final String firstName;
    private final String lastName;
    private ChainValidator validator;

    /**
     * Instantiates a new Name validator.
     *
     * @param firstName the first name
     * @param lastName  the last name
     */
    public NameValidator(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Instantiates a new Name validator.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param validator the validator
     */
    public NameValidator(String firstName, String lastName, ChainValidator validator) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.validator = validator;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (StringUtils.isNullOrEmpty(firstName) || StringUtils.isNullOrEmpty(lastName)
                || !firstName.matches(NAME_PATTERN) || !lastName.matches(NAME_PATTERN)
                || firstName.length() > MAX_LENGTH || lastName.length() > MAX_LENGTH) {
            serverMessage = Optional.of("serverMessage.invalid.name");
        }
        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }
        return serverMessage;
    }
}
