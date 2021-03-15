package com.epam.bar.validator;

import java.util.Map;
import java.util.Optional;

public interface Validator {
    Optional<String> validate(Map<String, String> parameters);
}
