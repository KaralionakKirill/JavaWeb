package com.epam.bar.validator;

import java.util.Optional;

public interface Validator {
    Optional<String> validate();
}
