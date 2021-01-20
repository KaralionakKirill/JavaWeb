package com.epam.JavaWeb.dao.impl;

import com.epam.JavaWeb.dao.BaseDao;
import com.epam.JavaWeb.dao.UserDao;
import com.epam.JavaWeb.db.ConnectionPool;
import com.epam.JavaWeb.entity.User;
import com.epam.JavaWeb.exception.DaoException;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

    @Language("SQL")
    private static final String SQL_INSERT_USER =
            "INSERT INTO users(login, password, email, first_name, last_name,  gender, date_of_birth) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?);";

    @Language("SQL")
    private static final String SQL_DELETE_USER =
            "DELETE FROM users WHERE login = ?;";

    @Language("SQL")
    private static final String SQL_UPDATE_USER =
            "UPDATE users SET password = ?, first_name = ?, last_name = ?, email = ?, gender = ?, " +
                    "date_of_birth = ? WHERE login = ?;";
    @Language("SQL")
    private static final String SQL_COUNT_USER =
            "SELECT count(*) FROM users ";

    @Language("SQL")
    private static final String SQL_SELECT_USER =
            "SELECT login, password, email, first_name, last_name,  gender, date_of_birth FROM users ";

    private static final UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean add(User entity, String password) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, entity.getEmail());
            preparedStatement.setString(4, entity.getFirstName());
            preparedStatement.setString(5, entity.getLastName());
            preparedStatement.setBoolean(6, entity.isGender());
            preparedStatement.setDate(7, null);
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public boolean remove(User entity) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER);
            preparedStatement.setString(1, entity.getLogin());
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public boolean update(User entity, String password) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, entity.getFirstName());
            preparedStatement.setString(3, entity.getLastName());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setBoolean(5, entity.isGender());
            preparedStatement.setDate(6, (Date) entity.getDateOfBirth());
            preparedStatement.setString(6, entity.getLogin());
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public Optional<List<User>> findAll() throws DaoException {
        Optional<List<User>> users;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User.Builder()
                        .setUserLogin(resultSet.getString("login"))
                        .setUserEmail(resultSet.getString("email"))
                        .setUserFirstName(resultSet.getString("first_name"))
                        .setUserLastName(resultSet.getString("last_name"))
                        .setUserGender(resultSet.getBoolean("gender"))
                        .setUserDateOfBirth(resultSet.getDate("date_of_birth"))
                        .build();
                userList.add(user);
            }
            users = Optional.of(userList);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return users;
    }
}
