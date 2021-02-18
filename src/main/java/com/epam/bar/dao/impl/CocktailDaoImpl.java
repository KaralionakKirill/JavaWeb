package com.epam.bar.dao.impl;

import com.epam.bar.dao.BaseDao;
import com.epam.bar.dao.FieldType;
import com.epam.bar.db.ConnectionPool;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.exception.DaoException;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class CocktailDaoImpl extends BaseDao<Cocktail, String> {
    @Language("SQL")
    private static final String SQL_INSERT_COCKTAIL =
            "INSERT INTO cocktail(cocktail_name, alcohol_id, composition, author_id, img_name, is_approved) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";

    public CocktailDaoImpl() {
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
}
