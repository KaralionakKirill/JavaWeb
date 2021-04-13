package com.epam.bar.dao;

import com.epam.bar.db.ConnectionPool;
import com.epam.bar.entity.Role;
import com.epam.bar.entity.User;
import com.epam.bar.exception.DaoException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.testng.Assert.assertTrue;


public class UserDaoTest {
    private static UserDao userDao;
    private static User firstUser;

    @BeforeAll
    public static void beforeAll() {
        userDao = new UserDao();
        firstUser = User.builder()
                .withLogin("First")
                .withEmail("first_email@mail.ru")
                .withRole(Role.USER)
                .build();
    }

    @AfterAll
    public static void afterAll() {
        userDao = null;
        firstUser = null;
        ConnectionPool.INSTANCE.destroyPool();
    }

    @Test
    public void addUserShouldReturnTrue() throws DaoException {
        final boolean actual = userDao.add(firstUser, "1");
        assertTrue(actual);
    }

    @Test
    public void deleteUserShouldReturnTrue() throws DaoException {
        final boolean actual = userDao.remove("First");
        assertTrue(actual);
    }
}