package com.epam.bar.dao;

import com.epam.bar.entity.Review;
import com.epam.bar.exception.DaoException;

import java.util.List;

/**
 * Provides CRUD operations for working with the {@link Review}
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public abstract class AbstractReviewDao extends BaseDao<String, Review> {
    /**
     * Find all cocktail reviews by cocktail id
     *
     * @param key the key
     * @return the list of cocktail reviews
     * @throws DaoException the dao exception
     */
    public abstract List<Review> findByCocktailId(String key) throws DaoException;
}
