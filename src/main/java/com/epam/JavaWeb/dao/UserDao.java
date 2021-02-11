package com.epam.JavaWeb.dao;

import com.epam.JavaWeb.entity.User;
import com.epam.JavaWeb.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    String findPassword(String key) throws DaoException;

    boolean addUser(User entity, String password) throws DaoException;

    List<User> findAll() throws DaoException;
}
