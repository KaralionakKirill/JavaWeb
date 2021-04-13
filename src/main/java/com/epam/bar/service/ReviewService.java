package com.epam.bar.service;

import com.epam.bar.dao.ReviewDao;
import com.epam.bar.entity.Review;
import com.epam.bar.exception.DaoException;
import com.epam.bar.exception.ServiceException;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * The class provides a business logic of application connected with {@link Review}
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class ReviewService {
    private final ReviewDao reviewDao;

    public ReviewService(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    /**
     * Add review.
     *
     * @param review the review
     * @throws ServiceException the service exception
     */
    public void addReview(Review review) throws ServiceException {
        try {
            reviewDao.add(review);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    /**
     * Find reviews by cocktail id
     *
     * @param id the id
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Review> findReviewsByCocktailId(String id) throws ServiceException {
        List<Review> reviews;
        try {
            reviews = reviewDao.findByCocktailId(id);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return reviews;
    }
}
