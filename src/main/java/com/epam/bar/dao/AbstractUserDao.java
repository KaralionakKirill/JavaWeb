package com.epam.bar.dao;

import com.epam.bar.entity.User;
import com.epam.bar.exception.DaoException;

import java.util.Optional;

/**
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public abstract class AbstractUserDao extends BaseDao<String, User> {

    /**
     * Find password
     *
     * @param key the key
     * @return the string
     * @throws DaoException the dao exception
     */
    public abstract String findPassword(String key) throws DaoException;

    /**
     * Add {@link User}
     *
     * @param entity   the entity
     * @param password the password
     * @return the boolean
     * @throws DaoException the dao exception
     */
    public abstract boolean add(User entity, String password) throws DaoException;

    /**
     * Find {@link User} by field
     *
     * @param key   the key
     * @param field the field
     * @return the optional
     * @throws DaoException the dao exception
     */
    public abstract Optional<User> findByField(String key, UserField field) throws DaoException;
}
