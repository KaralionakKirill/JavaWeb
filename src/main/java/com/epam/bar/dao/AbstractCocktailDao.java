package com.epam.bar.dao;

import com.epam.bar.entity.Cocktail;
import com.epam.bar.exception.DaoException;

import java.util.List;

/**
 * Provides CRUD operations for working with the {@link Cocktail}
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public abstract class AbstractCocktailDao extends BaseDao<String, Cocktail> {

    /**
     * Find {@link Cocktail} by field
     *
     * @param key   the key
     * @param field the field
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<Cocktail> findByField(String key, CocktailField field) throws DaoException;

    /**
     * Endorse cocktail
     *
     * @param id the cocktail id
     * @return the true if update successful
     * @throws DaoException the dao exception
     */
    public abstract boolean endorseCocktail(String id) throws DaoException;
}
