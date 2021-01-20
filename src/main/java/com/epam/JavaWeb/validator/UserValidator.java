package com.epam.JavaWeb.validator;

public class UserValidator {
    private static final String EMAIL_REGEX = "([a-z\\d_-]+\\.)*[a-z\\d_-]+@[a-z\\d_-]+(\\.[a-z\\d_-]+)*\\.[a-z]{2,6}";
    private static final String PASSWORD_REGEX = "[a-zA-Z\\d][a-zA-Z\\d_]{3,16}";
    private static final UserValidator instance = new UserValidator();

    private UserValidator() {
    }

    public static UserValidator getInstance(){
        return instance;
    }


    public boolean isValidEmail(String email) {
        return (email.matches(EMAIL_REGEX));
    }

    public boolean isValidPassword(String password) {
        return (password.matches(PASSWORD_REGEX));
    }
}
