package com.epam.bar.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * The class used for encoding passwords
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class PasswordEncoder {
    private PasswordEncoder() {
    }

    /**
     * Encryption string.
     *
     * @param value the value
     * @return the string
     */
    public static String encryption(String value) {
        return DigestUtils.md5Hex(value);
    }
}
