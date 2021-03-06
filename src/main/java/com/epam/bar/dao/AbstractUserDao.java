package com.epam.bar.dao;

import com.epam.bar.entity.User;
import com.epam.bar.exception.DaoException;

import java.util.Optional;

public abstract class AbstractUserDao extends BaseDao<String, User> {

    public abstract boolean changeRole(Long id, User.Role role) throws DaoException;

    public abstract String findPassword(String key) throws DaoException;

    public abstract boolean addUser(User entity, String password) throws DaoException;

    public abstract Optional<User> findByField(String key, FieldType fieldType) throws DaoException;
}
