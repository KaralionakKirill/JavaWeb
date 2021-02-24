package com.epam.bar.dao;

import com.epam.bar.db.ConnectionPool;
import com.epam.bar.entity.Role;
import com.epam.bar.entity.User;
import com.epam.bar.exception.DaoException;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class UserDao extends AbstractUserDao {

    @Language("SQL")
    private static final String SQL_INSERT_USER =
            "INSERT INTO base_user(login, password, email, role_id, first_name, last_name, " +
                    "activation_code, is_activate, loyalty_points) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

    @Language("SQL")
    private static final String SQL_DELETE_USER =
            "DELETE FROM base_user WHERE login = ?;";

    @Language("SQL")
    private static final String SQL_SELECT_PASSWORD =
            "SELECT password FROM base_user WHERE email = ?;";

    @Language("SQL")
    private static final String SQL_CHANGE_ROLE =
            "UPDATE base_user SET role_id = ? WHERE id = ?;";

    @Language("SQL")
    private static final String SQL_UPDATE_USER =
            "UPDATE base_user SET login = ?, email = ?, role_id = ?, first_name = ?, last_name = ?, activation_code = ?, " +
                    "is_activate = ?, loyalty_points = ? WHERE login = ?;";

    @Language("SQL")
    private static final String SQL_SELECT_USER =
            "SELECT bu.id, login, email, role_name, first_name, last_name, activation_code, is_activate, loyalty_points " +
                    "FROM base_user AS bu INNER JOIN user_role AS ur on bu.role_id = ur.id;";

    @Language("SQL")
    private static final String SQL_SELECT_BY_CODE =
            "SELECT bu.id, login, email, role_name, first_name, last_name, activation_code, is_activate, loyalty_points " +
                    "FROM base_user AS bu INNER JOIN user_role AS ur on bu.role_id = ur.id WHERE activation_code = ? ;";

    @Language("SQL")
    private static final String SQL_SELECT_BY_EMAIL =
            "SELECT bu.id, login, email, role_name, first_name, last_name, activation_code, is_activate, loyalty_points " +
                    "FROM base_user AS bu INNER JOIN user_role AS ur on bu.role_id = ur.id WHERE email = ?;";

    @Language("SQL")
    private static final String SQL_SELECT_BY_LOGIN =
            "SELECT login, email, role_name, first_name, last_name, activation_code, is_activate, loyalty_points " +
                    "FROM base_user AS bu INNER JOIN user_role AS ur on bu.role_id = ur.id WHERE login = ?;";

    @Language("SQL")
    private static final String SQL_SELECT_BY_FIRSTNAME =
            "SELECT bu.id, login, email, role_name, first_name, last_name, activation_code, is_activate, loyalty_points " +
                    "FROM base_user AS bu INNER JOIN user_role AS ur on bu.role_id = ur.id WHERE first_name = ?;";

    @Language("SQL")
    private static final String SQL_SELECT_BY_LASTNAME =
            "SELECT bu.id, login, email, role_name, first_name, last_name, activation_code, is_activate, loyalty_points " +
                    "FROM base_user AS bu INNER JOIN user_role AS ur on bu.role_id = ur.id WHERE last_name = ?;";

    public UserDao() {
    }

    private String sqlSelectByField(FieldType fieldType) {
        String sql;
        switch (fieldType) {
            case ACTIVATION_CODE:
                sql = SQL_SELECT_BY_CODE;
                break;
            case EMAIL:
                sql = SQL_SELECT_BY_EMAIL;
                break;
            case LOGIN:
                sql = SQL_SELECT_BY_LOGIN;
                break;
            case FIRSTNAME:
                sql = SQL_SELECT_BY_FIRSTNAME;
                break;
            case LASTNAME:
                sql = SQL_SELECT_BY_LASTNAME;
                break;
            default:
                sql = SQL_SELECT_USER;
        }
        return sql;
    }

    @Override
    public Optional<User> findByField(String name, FieldType fieldType) throws DaoException {
        Optional<User> optionalUser = Optional.empty();
        User user;
        String sql = sqlSelectByField(fieldType);
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = User.builder()
                        .withId(resultSet.getLong("id"))
                        .withLogin(resultSet.getString("login"))
                        .withEmail(resultSet.getString("email"))
                        .withRole(Role.valueOf(resultSet.getString("role_name")))
                        .withFirstName(resultSet.getString("first_name"))
                        .withLastName(resultSet.getString("last_name"))
                        .withActivationCode(resultSet.getString("activation_code"))
                        .withIsActivate(resultSet.getBoolean("is_activate"))
                        .withLoyaltyPoints(resultSet.getInt("loyalty_points"))
                        .build();
                optionalUser = Optional.of(user);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return optionalUser;
    }

    @Override
    public boolean changeRole(int id, Role role) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_ROLE)) {
            preparedStatement.setInt(1, role.getId());
            preparedStatement.setInt(2, id);
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return isUpdated;
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
            preparedStatement.setInt(4, entity.getRole().getId());
            preparedStatement.setString(5, entity.getFirstName());
            preparedStatement.setString(6, entity.getLastName());
            preparedStatement.setString(7, entity.getActivationCode());
            preparedStatement.setBoolean(8, entity.isActivate());
            preparedStatement.setInt(9, entity.getLoyaltyPoints());
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error(e);
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
            log.error(e);
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public boolean update(User entity, String key) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER)) {
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setInt(3, entity.getRole().getId());
            preparedStatement.setString(4, entity.getFirstName());
            preparedStatement.setString(5, entity.getLastName());
            preparedStatement.setString(6, entity.getActivationCode());
            preparedStatement.setBoolean(7, entity.isActivate());
            preparedStatement.setInt(8, entity.getLoyaltyPoints());
            preparedStatement.setString(9, key);
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error(e);
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
                        .withId(resultSet.getLong("id"))
                        .withLogin(resultSet.getString("login"))
                        .withEmail(resultSet.getString("email"))
                        .withRole(Role.valueOf(resultSet.getString("role_name")))
                        .withFirstName(resultSet.getString("first_name"))
                        .withLastName(resultSet.getString("last_name"))
                        .withActivationCode(resultSet.getString("activation_code"))
                        .withIsActivate(resultSet.getBoolean("is_activate"))
                        .withLoyaltyPoints(resultSet.getInt("loyalty_points"))
                        .build();
                users.add(user);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return users;
    }
}
