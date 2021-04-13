package com.epam.bar.service;

import com.epam.bar.dao.ReviewDao;
import com.epam.bar.entity.Review;
import com.epam.bar.exception.DaoException;
import com.epam.bar.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewDao reviewDao;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void findReviewsShouldReturn3Reviews() throws DaoException, ServiceException {
        //Given
        final Review firstReview = Review.builder().withId(1).build();
        final Review secondReview = Review.builder().withId(2).build();
        final Review thirdReview = Review.builder().withId(3).build();
        when(reviewDao.findByCocktailId("1")).thenReturn(List.of(firstReview, secondReview, thirdReview));
        final List<Review> expected = List.of(firstReview, secondReview, thirdReview);

        //When
        final List<Review> actual = reviewService.findReviewsByCocktailId("1");

        //Then
        assertEquals(actual, expected);
    }
}