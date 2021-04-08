package com.epam.bar.dao;

import com.epam.bar.db.ConnectionPool;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.entity.Review;
import com.epam.bar.entity.User;
import com.epam.bar.exception.DaoException;
import lombok.extern.log4j.Log4j2;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class ReviewDao extends AbstractReviewDao {
    @Language("SQL")
    private static final String SQL_UPDATE_USER_POINTS =
            "UPDATE base_user SET loyalty_points = loyalty_points + ? WHERE id=?";

    @Language("SQL")
    private static final String SQL_UPDATE_COCKTAIL_RATE =
            "UPDATE cocktail SET rate = (SELECT AVG(rate) FROM review WHERE cocktail_id = ?) WHERE id = ?";

    @Language("SQL")
    private static final String SQL_INSERT_REVIEW =
            "INSERT INTO review(rate, feedback, cocktail_id, user_id) VALUES (?, ?, ?, ?);";

    @Language("SQL")
    private static final String SQL_SELECT_BY_COCKTAIL_ID =
            "SELECT id, rate, feedback, cocktail_id, user_id FROM review WHERE cocktail_id = ?;";


    @Override
    public boolean add(Review entity) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatementReview = null;
            PreparedStatement preparedStatementUser = null;
            PreparedStatement preparedStatementCocktail = null;
            try {
                preparedStatementUser = connection.prepareStatement(SQL_UPDATE_USER_POINTS);
                preparedStatementUser.setInt(1, entity.getRate());
                preparedStatementUser.setLong(2, entity.getCocktail().getAuthor().getId());
                preparedStatementUser.executeUpdate();

                preparedStatementReview = connection.prepareStatement(SQL_INSERT_REVIEW);
                preparedStatementReview.setInt(1, entity.getRate());
                preparedStatementReview.setString(2, entity.getFeedback());
                preparedStatementReview.setInt(3, entity.getCocktail().getId());
                preparedStatementReview.setLong(4, entity.getAuthor().getId());
                preparedStatementReview.executeUpdate();

                preparedStatementCocktail = connection.prepareStatement(SQL_UPDATE_COCKTAIL_RATE);
                preparedStatementCocktail.setInt(1, entity.getCocktail().getId());
                preparedStatementCocktail.setInt(2, entity.getCocktail().getId());
                preparedStatementCocktail.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                log.error("Failed add review");
                connection.rollback();
                throw new DaoException(e);
            } finally {
                if (preparedStatementReview != null) {
                    preparedStatementReview.close();
                }
                if (preparedStatementUser != null) {
                    preparedStatementUser.close();
                }
                if (preparedStatementCocktail != null) {
                    preparedStatementCocktail.close();
                }
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return true;
    }

    @Override
    public boolean remove(String key) throws DaoException {
        throw new UnsupportedOperationException("Unsupported operation in ReviewDao");
    }

    @Override
    public boolean update(Review entity, String key) throws DaoException {
        throw new UnsupportedOperationException("Unsupported operation in ReviewDao");
    }

    @Override
    public List<Review> findAll() throws DaoException {
        throw new UnsupportedOperationException("Unsupported operation in ReviewDao");
    }

    private User findUser(String id) throws DaoException {
        UserDao userDao = new UserDao();
        Optional<User> user = userDao.findByField(id, UserField.ID);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DaoException("Cocktail is not found");
        }
    }

    private Cocktail findCocktail(String id) throws DaoException {
        CocktailDao cocktailDao = new CocktailDao();
        List<Cocktail> cocktails = cocktailDao.findByField(id, CocktailField.ID);
        if (cocktails.size() != 0) {
            return cocktails.get(0);
        } else {
            throw new DaoException("User is not found");
        }
    }

    @Override
    public List<Review> findByCocktailId(String key) throws DaoException {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_COCKTAIL_ID)) {
            preparedStatement.setString(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = findUser(resultSet.getString("user_id"));
                Cocktail cocktail = findCocktail(resultSet.getString("cocktail_id"));
                Review review = Review.builder()
                        .withId(resultSet.getInt("id"))
                        .withFeedback(resultSet.getString("feedback"))
                        .withRate(resultSet.getInt("rate"))
                        .withAuthor(user)
                        .withCocktail(cocktail)
                        .build();
                reviews.add(review);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return reviews;
    }
}
