package com.epam.JavaWeb.service;

import com.epam.JavaWeb.dao.impl.UserDaoImpl;
import com.epam.JavaWeb.entity.User;
import com.epam.JavaWeb.exception.DaoException;
import com.epam.JavaWeb.validator.UserValidator;

import java.util.List;
import java.util.Optional;

public class UserService {

    public boolean login(String email, String password) {
        UserValidator validator = UserValidator.getInstance();

        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();
        Optional<List<User>> users = Optional.empty();
        try {
            users = userDaoImpl.findAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return users.map(userList -> userList.stream().anyMatch(user1 -> user1.getEmail().equals(email))).orElse(false);
    }

    public boolean register(User user, String password) {
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();
        boolean result = false;
        try {
            result = userDaoImpl.add(user, password);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkUser(String emailValue, String passwordValue) {
        return false;
    }
}
