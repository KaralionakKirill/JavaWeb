package com.epam.bar.validator;

import java.util.Optional;

/**
 * The interface Chain validator.
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public interface ChainValidator {
    /**
     * Validate optional.
     *
     * @return the optional with invalid field or empty optional
     */
    Optional<String> validate();
}
