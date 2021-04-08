package com.epam.bar.validator.impl;

import com.epam.bar.validator.ChainValidator;

import javax.servlet.http.Part;
import java.util.Optional;

/**
 * Validate img file
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class ImgFileValidator implements ChainValidator {
    private static final String PNG_EXTENSION = ".png";
    private static final String JPG_EXTENSION = ".jpg";
    private final Part img;
    private ChainValidator validator;

    /**
     * Instantiates a new Img file validator.
     *
     * @param img       the img
     * @param validator the validator
     */
    public ImgFileValidator(Part img, ChainValidator validator) {
        this.validator = validator;
        this.img = img;
    }

    /**
     * Instantiates a new Img file validator.
     *
     * @param img the img
     */
    public ImgFileValidator(Part img) {
        this.img = img;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (img == null || img.getSubmittedFileName().isEmpty() ||
                (!img.getSubmittedFileName().endsWith(PNG_EXTENSION) && !img.getSubmittedFileName().endsWith(JPG_EXTENSION))) {
            serverMessage = Optional.of("serverMessage.invalid.img");
        }

        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }

        return serverMessage;
    }
}
