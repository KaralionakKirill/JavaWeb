package com.epam.bar.service;

import com.epam.bar.dao.UserDao;
import com.epam.bar.dao.UserField;
import com.epam.bar.entity.User;
import com.epam.bar.exception.DaoException;
import com.epam.bar.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @Test
    public void loginShouldReturnEmptyMessage() throws DaoException, ServiceException {
        //Given
        final User user = User.builder()
                .withEmail("email")
                .withBlocked(false)
                .withActivated(true)
                .build();
        when(userDao.findPassword("email")).thenReturn("827ccb0eea8a706c4c34a16891f84e7b");
        when(userDao.findByField("email", UserField.EMAIL)).thenReturn(Optional.of(user));
        final Optional<String> expected = Optional.empty();

        //When
        final Optional<String> actual = userService.login(user, "12345");

        //Then
        assertEquals(actual, expected);
    }

    @Test
    public void loginShouldReturnIncorrectUsernameOrPasswordWhenPasswordIsIncorrect() throws DaoException, ServiceException {
        //Given
        final User user = User.builder()
                .withEmail("email")
                .withBlocked(false)
                .withActivated(true)
                .build();
        when(userDao.findPassword("email")).thenReturn("827ccb0eea8a706c4c34a16891f84e7b");
        final Optional<String> expected = Optional.of("serverMessage.incorrectUsernameOrPassword");

        //When
        final Optional<String> actual = userService.login(user, "1234ab");

        //Then
        assertEquals(actual, expected);
    }


    @Test
    public void loginShouldReturnActivatePlease() throws DaoException, ServiceException {
        //Given
        final User user = User.builder()
                .withEmail("email")
                .withBlocked(false)
                .withActivated(false)
                .build();
        when(userDao.findPassword("email")).thenReturn("827ccb0eea8a706c4c34a16891f84e7b");
        when(userDao.findByField("email", UserField.EMAIL)).thenReturn(Optional.of(user));
        final Optional<String> expected = Optional.of("serverMessage.activateAccountPlease");

        //When
        final Optional<String> actual = userService.login(user, "12345");

        //Then
        assertEquals(actual, expected);
    }


    @Test
    public void registerShouldReturnEmptyMessage() throws ServiceException {
        //Given
        final User user = User.builder()
                .withLogin("login")
                .withEmail("email")
                .withBlocked(false)
                .withActivated(false)
                .build();
        when(userService.findByField(user.getEmail(), UserField.EMAIL)).thenReturn(Optional.empty());
        when(userService.findByField(user.getLogin(), UserField.LOGIN)).thenReturn(Optional.empty());
        final Optional<String> expected = Optional.empty();

        //When
        final Optional<String> actual = userService.register(user, "12345");

        //Then
        assertEquals(actual, expected);
    }

    @Test
    public void registerShouldReturnUsernameAlreadyTaken() throws ServiceException {
        //Given
        final User user = User.builder()
                .withLogin("login")
                .withEmail("email")
                .withBlocked(false)
                .withActivated(false)
                .build();
        when(userService.findByField(user.getLogin(), UserField.LOGIN)).thenReturn(
                Optional.of(User.builder().withLogin("login").build()));
        final Optional<String> expected = Optional.of("serverMessage.usernameAlreadyTaken");

        //When
        final Optional<String> actual = userService.register(user, "12345");

        //Then
        assertEquals(actual, expected);
    }


    @Test
    public void activateUserShouldReturnEmptyMessage() throws ServiceException {
        //Given
        final User user = User.builder().withId(1L).build();
        when(userService.findByField("1111", UserField.ACTIVATION_CODE)).thenReturn(Optional.of(user));
        final Optional<String> expected = Optional.empty();

        //When
        final Optional<String> actual = userService.activateUser("1111");

        //Then
        assertEquals(actual, expected);
    }
}