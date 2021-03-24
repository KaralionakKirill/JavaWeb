package com.epam.bar.dao;

import com.epam.bar.entity.Review;
import com.epam.bar.exception.DaoException;

import java.util.List;

public abstract class AbstractReviewDao extends BaseDao<String, Review> {
    public abstract List<Review> findByCocktailId(String key) throws DaoException;
}
