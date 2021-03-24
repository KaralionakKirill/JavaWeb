package com.epam.bar.service;

import com.epam.bar.dao.UserDao;
import com.epam.bar.dao.field.UserField;
import com.epam.bar.entity.User;
import com.epam.bar.exception.DaoException;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.util.ActivationMailSender;
import com.epam.bar.util.PasswordEncoder;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;

@Log4j2
public class UserService {
    private final UserDao userDao = new UserDao();

    public Optional<String> login(User user, String password) throws ServiceException {
        Optional<String> serverMessage = Optional.empty();
        String email = user.getEmail();
        password = PasswordEncoder.encryption(password);
        try {
            String dbPassword = userDao.findPassword(email);
            if (password.equals(dbPassword)) {
                User dbUser = findByField(email, UserField.EMAIL).get();
                if(!dbUser.isBlocked()) {
                    if (dbUser.isActivated()) {
                        user.setId(dbUser.getId());
                        user.setLogin(dbUser.getLogin());
                        user.setRole(dbUser.getRole());
                        user.setFirstName(dbUser.getFirstName());
                        user.setLastName(dbUser.getLastName());
                        user.setLoyaltyPoints(dbUser.getLoyaltyPoints());
                    } else {
                        serverMessage = Optional.of("serverMessage.activateAccountPlease");
                    }
                }else{
                    serverMessage = Optional.of("serverMessage.blockedAccount");
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
        password = PasswordEncoder.encryption(password);
        if (findByField(user.getEmail(), UserField.EMAIL).isEmpty()) {
            if (findByField(user.getLogin(), UserField.LOGIN).isEmpty()) {
                String code = UUID.randomUUID().toString();
                Runnable emailSender = new ActivationMailSender(code, user.getEmail());
                Executors.newSingleThreadExecutor().submit(emailSender);
                try {
                    user.setActivationCode(code);
                    userDao.add(user, password);
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

    public List<User> findAllUsers() throws ServiceException {
        List<User> users;
        try {
            users = userDao.findAll();
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return users;
    }

    public Optional<String> updateUser(User user) throws ServiceException {
        Optional<String> serverMessage = Optional.empty();
        try {
            String id = user.getId().toString();
            if (!userDao.update(user, id)) {
                serverMessage = Optional.of("serverMessage.updateUserException");
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return serverMessage;
    }

    public Optional<String> activateUser(String activationCode) throws ServiceException {
        Optional<String> serverMessage = Optional.empty();
        Optional<User> userOptional = findByField(activationCode, UserField.ACTIVATION_CODE);
        if (userOptional.isPresent()) {
            try {
                User user = userOptional.get();
                user.setActivationCode(null);
                user.setActivated(true);
                userDao.update(user, user.getId().toString());
            } catch (DaoException e) {
                log.error(e);
                throw new ServiceException(e);
            }
        } else {
            serverMessage = Optional.of("serverMessage.incorrectActivationCode");
        }
        return serverMessage;
    }

    public Optional<User> findByField(String id, UserField field) throws ServiceException {
        Optional<User> user;
        try {
            user = userDao.findByField(id, field);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return user;
    }
}
