package com.epam.bar.dao;

import com.epam.bar.entity.User;
import com.epam.bar.exception.DaoException;

import java.util.List;

public interface UserDao {

    String findPassword(String key) throws DaoException;

    boolean addUser(User entity, String password) throws DaoException;

    List<User> findAll() throws DaoException;
}
