package com.epam.bar.dao;

import com.epam.bar.entity.Entity;
import com.epam.bar.entity.User;
import com.epam.bar.exception.DaoException;

import java.util.List;
import java.util.Optional;

public abstract class BaseDao<K, T extends Entity> {

    public abstract boolean add(T entity) throws DaoException;

    public abstract boolean remove(K key) throws DaoException;

    public abstract boolean update(T entity, K key) throws DaoException;

    public abstract List<T> findAll() throws DaoException;

}
