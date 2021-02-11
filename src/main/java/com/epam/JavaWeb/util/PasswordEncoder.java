package com.epam.JavaWeb.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncoder {
    private PasswordEncoder() {
    }

    public static String encryption(String value) {
        return DigestUtils.md5Hex(value);
    }
}
