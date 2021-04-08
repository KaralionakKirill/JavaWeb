package com.epam.bar.dao;

import com.epam.bar.entity.Entity;
import com.epam.bar.exception.DaoException;

import java.util.List;

/**
 * Provides basic CRUD operations for working with entities
 *
 * @param <K> the key parameter
 * @param <T> the entity parameter
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public abstract class BaseDao<K, T extends Entity> {

    /**
     * Add entity
     *
     * @param entity the entity
     * @return if passed successfully returned true else false
     * @throws DaoException the dao exception
     */
    public abstract boolean add(T entity) throws DaoException;

    /**
     * Remove entity
     *
     * @param key the key
     * @return if passed successfully returned true else false
     * @throws DaoException the dao exception
     */
    public abstract boolean remove(K key) throws DaoException;

    /**
     * Update entity
     *
     * @param entity the entity
     * @param key    the key
     * @return if passed successfully returned true else false
     * @throws DaoException the dao exception
     */
    public abstract boolean update(T entity, K key) throws DaoException;

    /**
     * Find all entities
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    public abstract List<T> findAll() throws DaoException;

}
