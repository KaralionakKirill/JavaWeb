package com.epam.bar.service;

import com.epam.bar.dao.ReviewDao;
import com.epam.bar.entity.Review;
import com.epam.bar.exception.DaoException;
import com.epam.bar.exception.ServiceException;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ReviewService {
    private final ReviewDao reviewDao = new ReviewDao();

    public void addReview(Review review) throws ServiceException {
        try {
            reviewDao.add(review);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

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
