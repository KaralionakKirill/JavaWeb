package com.epam.JavaWeb.service;

import com.epam.JavaWeb.dao.impl.UserDaoImpl;
import com.epam.JavaWeb.entity.User;
import com.epam.JavaWeb.exception.DaoException;
import com.epam.JavaWeb.exception.ServiceException;
import com.epam.JavaWeb.util.PasswordEncoder;
import com.epam.JavaWeb.validator.UserValidator;

public class UserService {

    public boolean login(String email, String password) throws ServiceException {
        if(!UserValidator.isValidEmail(email) && !UserValidator.isValidPassword(password)){
            throw new ServiceException("Is not correct email or password");
        }
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();
        password = PasswordEncoder.encryption(password);
        String dbPassword;
        try {
            dbPassword = userDaoImpl.findPassword(email);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return dbPassword.equals(password);
    }

    public boolean register(User user, String password) throws DaoException {
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();
        boolean result;
        try {
            password = PasswordEncoder.encryption(password);
            result = userDaoImpl.addUser(user, password);
        } catch (DaoException e) {
            throw new DaoException(e);
        }
        return result;
    }
}
