package com.epam.JavaWeb.service;

import com.epam.JavaWeb.dao.Field;
import com.epam.JavaWeb.dao.impl.UserDaoImpl;
import com.epam.JavaWeb.entity.User;
import com.epam.JavaWeb.exception.DaoException;
import com.epam.JavaWeb.exception.ServiceException;
import com.epam.JavaWeb.util.ActivationMailSender;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;

@Log4j2
public class UserService {

    public Optional<String> login(String email, String password, HashMap<String, Object> session) throws ServiceException {
        Optional<String> serverMessage = Optional.empty();
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();
        try {
            String dbPassword = userDaoImpl.findPassword(email);
            if (password.equals(dbPassword)) {
                User user = findByEmail(email).get();
                if (user.isActivate()) {
                    session.put("user", user);
                }else{
                    serverMessage = Optional.of("serverMessage.activateAccountPlease");
                }
            } else {
                serverMessage = Optional.of("serverMessage.incorrectUsernameOrPassword");
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return serverMessage;
    }

    public Optional<String> register(User user, String password) throws ServiceException {
        Optional<String> serverMessage = Optional.empty();
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();
        if (findByEmail(user.getEmail()).isEmpty()) {
            if (findByUsername(user.getLogin()).isEmpty()) {
                String code = UUID.randomUUID().toString();
                Runnable emailSender = new ActivationMailSender(code, user.getEmail());
                Executors.newSingleThreadExecutor().submit(emailSender);
                try {
                    user.setActivationCode(code);
                    userDaoImpl.addUser(user, password);
                } catch (DaoException e) {
                    log.error(e);
                    throw new ServiceException(e);
                }
            } else {
                serverMessage = Optional.of("serverMessage.usernameAlreadyTaken");
            }
        } else {
            serverMessage = Optional.of("serverMessage.emailAlreadyTaken");
        }
        return serverMessage;
    }

    private Optional<User> findByUsername(String name) throws ServiceException {
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();
        Optional<User> user;
        try {
            user = userDaoImpl.findByField(name, Field.LOGIN);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return user;
    }

    private Optional<User> findByEmail(String email) throws ServiceException {
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();
        Optional<User> user;
        try {
            user = userDaoImpl.findByField(email, Field.EMAIL);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return user;
    }

    public Optional<String> activateUser(String activationCode) throws ServiceException {
        Optional<String> serverMessage = Optional.empty();
        Optional<User> userOptional = findByActivationCode(activationCode);
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();
        if (userOptional.isPresent()) {
            try {
                User user = userOptional.get();
                user.setActivationCode(null);
                user.setActivate(true);
                userDaoImpl.update(user, user.getLogin());
            } catch (DaoException e) {
                log.error(e);
                throw new ServiceException(e);
            }
        } else {
            serverMessage = Optional.of("serverMessage.incorrectActivationCode");
        }
        return serverMessage;
    }

    public Optional<User> findByActivationCode(String activationCode) throws ServiceException {
        UserDaoImpl userDaoImpl = UserDaoImpl.getInstance();
        Optional<User> user;
        try {
            user = userDaoImpl.findByField(activationCode, Field.ACTIVATION_CODE);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return user;
    }
}
