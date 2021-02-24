package com.epam.bar.service;

import com.epam.bar.dao.FieldType;
import com.epam.bar.dao.UserDao;
import com.epam.bar.entity.Role;
import com.epam.bar.entity.User;
import com.epam.bar.exception.DaoException;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.util.ActivationMailSender;
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
        try {
            String dbPassword = userDao.findPassword(email);
            if (password.equals(dbPassword)) {
                User dbUser = findByEmail(email).get();
                if (dbUser.isActivate()) {
                    user.setId(dbUser.getId());
                    user.setLogin(dbUser.getLogin());
                    user.setRole(dbUser.getRole());
                    user.setFirstName(dbUser.getFirstName());
                    user.setLastName(dbUser.getLastName());
                    user.setLoyaltyPoints(dbUser.getLoyaltyPoints());
                    user.setActivate(dbUser.isActivate());
                    user.setBlocked(dbUser.isBlocked());
                } else {
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
        if (findByEmail(user.getEmail()).isEmpty()) {
            if (findByUsername(user.getLogin()).isEmpty()) {
                String code = UUID.randomUUID().toString();
                Runnable emailSender = new ActivationMailSender(code, user.getEmail());
                Executors.newSingleThreadExecutor().submit(emailSender);
                try {
                    user.setActivationCode(code);
                    userDao.addUser(user, password);
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

    public Optional<String> changeUserRole(int id, Role role) throws ServiceException {
        Optional<String> serverMessage = Optional.empty();
        try {
            userDao.changeRole(id, role);//todo
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return serverMessage;
    }

    public Optional<String> activateUser(String activationCode) throws ServiceException {
        Optional<String> serverMessage = Optional.empty();
        Optional<User> userOptional = findByActivationCode(activationCode);
        if (userOptional.isPresent()) {
            try {
                User user = userOptional.get();
                user.setActivationCode(null);
                user.setActivate(true);
                userDao.update(user, user.getLogin());
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
        Optional<User> user;
        try {
            user = userDao.findByField(activationCode, FieldType.ACTIVATION_CODE);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return user;
    }

    private Optional<User> findByUsername(String name) throws ServiceException {
        Optional<User> user;
        try {
            user = userDao.findByField(name, FieldType.LOGIN);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return user;
    }

    private Optional<User> findByEmail(String email) throws ServiceException {
        Optional<User> user;
        try {
            user = userDao.findByField(email, FieldType.EMAIL);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return user;
    }
}
