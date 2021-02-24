package com.epam.bar.dao;

import com.epam.bar.db.ConnectionPool;
import com.epam.bar.entity.Alcohol;
import com.epam.bar.entity.Cocktail;
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
public class CocktailDao extends BaseDao<String, Cocktail> {
    @Language("SQL")
    private static final String SQL_INSERT_COCKTAIL =
            "INSERT INTO cocktail(cocktail_name, alcohol_id, composition, author_id, img_name, is_approved) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";

    @Language("SQL")
    private static final String SQL_SELECT_COCKTAIL =
            "SELECT cocktail_name, alcohol_name, composition, img_name " +
                    "FROM cocktail INNER JOIN alcohol on cocktail.alcohol_id = alcohol.id";

    public CocktailDao() {
    }

    @Override
    public boolean add(Cocktail entity) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_COCKTAIL)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setInt(2, entity.getAlcohol().getId());
            preparedStatement.setString(3, entity.getComposition());
            preparedStatement.setInt(4, entity.getAuthorId());
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
        return false;
    }

    @Override
    public boolean update(Cocktail entity, String key) throws DaoException {
        return false;
    }

    @Override
    public Optional<Cocktail> findByField(String key, FieldType fieldType) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Cocktail> findAll() throws DaoException {
        List<Cocktail> cocktails = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_COCKTAIL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Cocktail cocktail = Cocktail.builder()
                        .withName(resultSet.getString("cocktail_name"))
                        .withAlcohol(Alcohol.valueOf(resultSet.getString("alcohol_name")))
                        .withComposition(resultSet.getString("composition"))
                        .withImgName(resultSet.getString("img_name"))
                        .build();
                cocktails.add(cocktail);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return cocktails;
    }
}
