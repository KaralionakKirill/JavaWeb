package com.epam.JavaWeb.dao;

import com.epam.JavaWeb.entity.Entity;
import com.epam.JavaWeb.exception.DaoException;

import java.util.Optional;

public abstract class BaseDao<T extends Entity, K> {

    public abstract boolean add(T entity) throws DaoException;

    public abstract boolean remove(K key) throws DaoException;

    public abstract boolean update(T entity, K key) throws DaoException;

    public abstract Optional<T> find(K key) throws DaoException;

}
