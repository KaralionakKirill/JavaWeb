package com.epam.bar.dao;

import com.epam.bar.db.ConnectionPool;
import com.epam.bar.entity.Alcohol;
import com.epam.bar.entity.Cocktail;
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

/**
 * Provides CRUD operations for working with the {@link User}
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class CocktailDao extends AbstractCocktailDao {
    @Language("SQL")
    private static final String SQL_DELETE_COCKTAIL = " DELETE FROM cocktail WHERE id = ?";

    @Language("SQL")
    private static final String SQL_UPDATE_COCKTAIL =
            "UPDATE cocktail SET cocktail_name = ?, alcohol_id = ?, composition = ?, author_id = ?, " +
                    "img_name = ?, rate = ?, is_approved = ? WHERE id = ?;";

    @Language("SQL")
    private static final String SQL_ENDORSE_COCKTAIL =
            "UPDATE cocktail SET is_approved = ? WHERE id = ?;";

    @Language("SQL")
    private static final String SQL_INSERT_COCKTAIL =
            "INSERT INTO cocktail(cocktail_name, alcohol_id, composition, author_id, img_name, is_approved) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";

    @Language("SQL")
    private static final String SQL_SELECT_COCKTAIL =
            "SELECT cocktail.id, cocktail_name, author_id, alcohol_name, rate, composition, img_name, is_approved  " +
                    "FROM cocktail INNER JOIN alcohol ON cocktail.alcohol_id = alcohol.id";
    @Language("SQL")
    private static final String SQL_SELECT__BY_ID =
            "SELECT cocktail.id, cocktail_name, author_id, alcohol_name, rate, composition, img_name, is_approved  " +
                    "FROM cocktail INNER JOIN alcohol ON cocktail.alcohol_id = alcohol.id WHERE cocktail.id = ?";

    @Language("SQL")
    private static final String SQL_SELECT_BY_ALCOHOL =
            "SELECT cocktail.id, cocktail_name, author_id, alcohol_name, rate, composition, img_name, is_approved  " +
                    "FROM cocktail INNER JOIN alcohol ON cocktail.alcohol_id = alcohol.id WHERE alcohol_id = ?";


    private String sqlSelectByField(CocktailField field) throws DaoException {
        String sql;
        switch (field) {
            case ALCOHOL:
                sql = SQL_SELECT_BY_ALCOHOL;
                break;
            case ID:
                sql = SQL_SELECT__BY_ID;
                break;
            default:
                throw new DaoException("This field does nor exist : " + field.name().toLowerCase());
        }
        return sql;
    }


    @Override
    public boolean add(Cocktail entity) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_COCKTAIL)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getAlcohol().getId());
            preparedStatement.setString(3, entity.getComposition());
            preparedStatement.setLong(4, entity.getAuthor().getId());
            preparedStatement.setString(5, entity.getImgName());
            preparedStatement.setBoolean(6, entity.isApproved());
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public boolean remove(String key) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COCKTAIL)) {
            preparedStatement.setString(1, key);
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public boolean update(Cocktail entity, String key) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COCKTAIL)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getAlcohol().getId());
            preparedStatement.setString(3, entity.getComposition());
            preparedStatement.setLong(4, entity.getAuthor().getId());
            preparedStatement.setString(5, entity.getImgName());
            preparedStatement.setDouble(6, entity.getRate());
            preparedStatement.setBoolean(7, entity.isApproved());
            preparedStatement.setString(8, key);
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public List<Cocktail> findByField(String key, CocktailField field) throws DaoException {
        List<Cocktail> cocktails = new ArrayList<>();
        String sql = sqlSelectByField(field);
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, key);
            parseResultSet(cocktails, preparedStatement);
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return cocktails;
    }

    private User findUser(String id) throws DaoException {
        UserDao userDao = new UserDao();
        Optional<User> user = userDao.findByField(id, UserField.ID);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DaoException("User is not found");
        }
    }

    @Override
    public boolean endorseCocktail(String id) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_ENDORSE_COCKTAIL)) {
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, id);
            isUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public List<Cocktail> findAll() throws DaoException {
        List<Cocktail> cocktails = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COCKTAIL)) {
            parseResultSet(cocktails, preparedStatement);
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return cocktails;
    }

    private void parseResultSet(List<Cocktail> cocktails, PreparedStatement preparedStatement) throws SQLException, DaoException {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            User user = findUser(resultSet.getString("author_id"));
            Cocktail cocktail = Cocktail.builder()
                    .withId(resultSet.getInt("cocktail.id"))
                    .withName(resultSet.getString("cocktail_name"))
                    .withAuthor(user)
                    .withAlcohol(Alcohol.valueOf(resultSet.getString("alcohol_name")))
                    .withComposition(resultSet.getString("composition"))
                    .withImgName(resultSet.getString("img_name"))
                    .withRate(resultSet.getDouble("rate"))
                    .withApproved(resultSet.getBoolean("is_approved"))
                    .build();
            cocktails.add(cocktail);
        }
    }
}
