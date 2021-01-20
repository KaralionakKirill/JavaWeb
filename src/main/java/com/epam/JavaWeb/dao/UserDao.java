package com.epam.JavaWeb.dao;

import com.epam.JavaWeb.entity.User;
import com.epam.JavaWeb.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    boolean add(User entity, String password) throws DaoException;

    boolean remove(User entity) throws DaoException;

    boolean update(User entity, String password) throws DaoException;

    Optional<List<User>> findAll() throws DaoException;
}
