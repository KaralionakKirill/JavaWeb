package com.epam.JavaWeb.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordEncoder {
    private PasswordEncoder() {
    }

    public static void encryption(String value) {
        DigestUtils.md5Hex(value);
    }
}
