package com.epam.bar.dao;

import com.epam.bar.entity.Entity;
import com.epam.bar.exception.DaoException;

import java.util.Optional;

public abstract class BaseDao<T extends Entity, K> {

    public abstract boolean add(T entity) throws DaoException;

    public abstract boolean remove(K key) throws DaoException;

    public abstract boolean update(T entity, K key) throws DaoException;

    public abstract Optional<T> findByField(K key, FieldType fieldType) throws DaoException;

}
