package com.epam.JavaWeb.validator;

public class UserValidator {
    private static final String EMAIL_REGEX = "([A-Za-z\\d_-]+\\.)*[a-z\\d_-]+@[A-Za-z\\d_-]+(\\.[A-Za-z\\d_-]+)*\\.[a-z]{2,6}";
    private static final String PASSWORD_REGEX = "[a-zA-Z\\d][a-zA-Z\\d_]{3,16}";

    private UserValidator() {
    }

    public static boolean isValidEmail(String email) {
        return (email.matches(EMAIL_REGEX));
    }

    public static boolean isValidPassword(String password) {
        return (password.matches(PASSWORD_REGEX));
    }
}
