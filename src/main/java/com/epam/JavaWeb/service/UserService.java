package com.epam.JavaWeb.service;

import com.epam.JavaWeb.dao.impl.UserDaoImpl;
import com.epam.JavaWeb.entity.User;
import com.epam.JavaWeb.exception.DaoException;
import com.epam.JavaWeb.exception.ServiceException;
import com.epam.JavaWeb.util.PasswordEncoder;
import com.epam.JavaWeb.validator.UserValidator;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
public class UserService {

    public Optional<String> login(String email, String password) throws ServiceException {
        Optional<String> serverMessage = Optional.empty();
        if(!UserValidator.isValidEmail(email) && !UserValidator.isValidPassword(password)){
            return Optional.of("serverMessage.incorrectData");
        }
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();
        try {
            String dbPassword = userDaoImpl.findPassword(email);
            PasswordEncoder.encryption(password);
            if(dbPassword == null){
                serverMessage = Optional.of("serverMessage.notFoundUser");
            }else{
                serverMessage = dbPassword.
                        equals(password) ? serverMessage:
                        Optional.of("serverMessage.noMatchesUser");
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return serverMessage;
    }

    public Optional<String> register(User user, String password) throws DaoException {
        Optional<String> serverMessage = Optional.empty();
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();
        try {
            PasswordEncoder.encryption(password);
            if(userDaoImpl.addUser(user, password)){
                serverMessage = Optional.of("serverMessage.registrationEx");//todo
            }
        } catch (DaoException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return serverMessage;
    }
}
