package com.epam.bar.service;

import com.epam.bar.dao.UserDao;
import com.epam.bar.dao.UserField;
import com.epam.bar.entity.User;
import com.epam.bar.exception.DaoException;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.util.PasswordEncoder;
import com.epam.bar.util.mail.ActivationMailSender;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;

/**
 * The class provides a business logic of application connected with {@link User}
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Login user
     *
     * @param user     the user
     * @param password the password
     * @return the optional with server message or empty optional
     * @throws ServiceException the service exception
     */
    public Optional<String> login(User user, String password) throws ServiceException {
        Optional<String> serverMessage = Optional.empty();
        String email = user.getEmail();
        password = PasswordEncoder.encryption(password);
        try {
            String dbPassword = userDao.findPassword(email);
            Optional<User> userOptional = findByField(email, UserField.EMAIL);
            if (password.equals(dbPassword) && userOptional.isPresent()) {
                User dbUser = userOptional.get();
                if (!dbUser.isBlocked()) {
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
                } else {
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

    /**
     * Register user
     *
     * @param user     the user
     * @param password the password
     * @return the optional with server message or empty optional
     * @throws ServiceException the service exception
     */
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

    /**
     * Find all users
     *
     * @return the list
     * @throws ServiceException the service exception
     */
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

    /**
     * Update user
     *
     * @param user the user
     * @return the optional with server message or empty optional
     * @throws ServiceException the service exception
     */
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

    /**
     * Activate user
     *
     * @param activationCode the activation code
     * @return the optional with server message or empty optional
     * @throws ServiceException the service exception
     */
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

    /**
     * Find user by field
     *
     * @param id    the id
     * @param field the field
     * @return the optional
     * @throws ServiceException the service exception
     */
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
