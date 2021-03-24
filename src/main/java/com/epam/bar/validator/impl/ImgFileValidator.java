package com.epam.bar.validator.impl;

import com.epam.bar.validator.Validator;

import javax.servlet.http.Part;
import java.util.Optional;

public class ImgFileValidator implements Validator {
    private final Part img;
    private Validator validator = null;

    public ImgFileValidator(Part img, Validator validator) {
        this.validator = validator;
        this.img = img;
    }

    public ImgFileValidator(Part img) {
        this.img = img;
    }

    @Override
    public Optional<String> validate() {
        Optional<String> serverMessage = Optional.empty();
        if (img == null || img.getSubmittedFileName().isEmpty() ||
                (!img.getSubmittedFileName().endsWith(".png") && !img.getSubmittedFileName().endsWith(".jpg"))) {
            serverMessage = Optional.of("serverMessage.invalid.img");
        }

        if (validator != null && serverMessage.isEmpty()) {
            serverMessage = validator.validate();
        }

        return serverMessage;
    }
}
