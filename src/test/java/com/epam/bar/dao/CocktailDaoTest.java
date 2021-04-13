package com.epam.bar.dao;

import com.epam.bar.db.ConnectionPool;
import com.epam.bar.entity.Alcohol;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.entity.User;
import com.epam.bar.exception.DaoException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.testng.Assert.assertTrue;

public class CocktailDaoTest {
    private static CocktailDao cocktailDao;

    @BeforeAll
    public static void beforeAll() {
        cocktailDao = new CocktailDao();
    }

    @AfterAll
    public static void afterAll() {
        cocktailDao = null;
        ConnectionPool.INSTANCE.destroyPool();
    }

    @Test
    public void addUserShouldReturnTrue() throws DaoException {
        User author = User.builder()
                .withId(32L)
                .build();
        Cocktail cocktail = Cocktail.builder()
                .withName("TestCocktail")
                .withComposition("composition")
                .withAlcohol(Alcohol.GIN)
                .withAuthor(author)
                .withImgName("file")
                .withApproved(true)
                .build();
        final boolean actual = cocktailDao.add(cocktail);
        assertTrue(actual);
    }

    @Test
    public void deleteUserShouldReturnTrue() throws DaoException {
        final boolean actual = cocktailDao.remove("46");
        assertTrue(actual);
    }
}