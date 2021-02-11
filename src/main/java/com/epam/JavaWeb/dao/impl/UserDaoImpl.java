package com.epam.JavaWeb.dao.impl;

import com.epam.JavaWeb.dao.BaseDao;
import com.epam.JavaWeb.dao.Field;
import com.epam.JavaWeb.dao.UserDao;
import com.epam.JavaWeb.db.ConnectionPool;
import com.epam.JavaWeb.entity.User;
import com.epam.JavaWeb.exception.DaoException;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class UserDaoImpl extends BaseDao<User, String> implements UserDao {

    @Language("SQL")
    private static final String SQL_INSERT_USER =
            "INSERT INTO users(login, password, email, activation_code, is_activate) " +
                    "VALUES (?, ?, ?, ?, ?);";

    @Language("SQL")
    private static final String SQL_DELETE_USER =
            "DELETE FROM users WHERE login = ?;";

    @Language("SQL")
    private static final String SQL_SELECT_PASSWORD =
            "SELECT password FROM users WHERE email = ?;";

    @Language("SQL")
    private static final String SQL_UPDATE_USER =
            "UPDATE users SET first_name = ?, last_name = ?, email = ?, is_activate = ? WHERE login = ?;";

    @Language("SQL")
    private static final String SQL_SELECT_USER =
            "SELECT login, email, first_name, last_name, date_of_birth FROM users;";


    private static final UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    private String sqlSelectByField(Field field){
        StringBuilder stringBuilder = new StringBuilder("SELECT login, email, activation_code, is_activate, FROM users WHERE ");
        switch (field){
            case ACTIVATION_CODE:
                stringBuilder.append("activation_code = ?");
                break;
            case EMAIL:
                stringBuilder.append("email = ?");
                break;
            case LOGIN:
                stringBuilder.append("login = ?");
                break;
            case FIRSTNAME:
                stringBuilder.append("first_name = ?");
                break;
            case LASTNAME:
                stringBuilder.append("last_name = ?");
                break;
        }
        return stringBuilder.toString();//todo
    }

    @Override
    public Optional<User> findByField(String name, Field field) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        User user;
        String sql = sqlSelectByField(field);
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = User.builder()
                        .withLogin(resultSet.getString("login"))
                        .withEmail(resultSet.getString("email"))
                        .withActivationCode(resultSet.getString("activation_code"))
                        .withIsActivate(resultSet.getBoolean("is_activate"))
                        .build();
                optionalUser = Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return optionalUser;
    }

    @Override
    public String findPassword(String key) throws DaoException {
        String password = null;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PASSWORD)) {
            preparedStatement.setString(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                password = resultSet.getString("password");
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return password;
    }

    @Override
    public boolean addUser(User entity, String password) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER)) {
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getActivationCode());
            preparedStatement.setBoolean(5, entity.isActivate());
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public boolean add(User entity) throws DaoException {
        throw new UnsupportedOperationException("Unsupported operation in UserDao!");
    }

    @Override
    public boolean remove(String key) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)) {
            preparedStatement.setString(1, key);
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public boolean update(User entity, String key) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setBoolean(4, entity.isActivate());
            preparedStatement.setString(5, key);
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = User.builder()
                        .withLogin(resultSet.getString("login"))
                        .withEmail(resultSet.getString("email"))
                        .withFirstName(resultSet.getString("first_name"))
                        .withLastName(resultSet.getString("last_name"))
                        .build();
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return users;
    }
}
