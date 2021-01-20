package com.epam.JavaWeb.command.impl;

import com.epam.JavaWeb.command.*;
import com.epam.JavaWeb.entity.User;
import com.epam.JavaWeb.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.HashMap;

public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private final UserService service;

    public RegistrationCommand(UserService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String login = requestContext.getRequestParameters().get(RequestParameter.LOGIN);
        String password = requestContext.getRequestParameters().get(RequestParameter.PASSWORD);
        String firstname = requestContext.getRequestParameters().get(RequestParameter.FIRST_NAME);
        String lastname = requestContext.getRequestParameters().get(RequestParameter.LAST_NAME);
        String birthdate = requestContext.getRequestParameters().get(RequestParameter.DATE_OF_BIRTH);
        String email = requestContext.getRequestParameters().get(RequestParameter.EMAIL);
        String gender = requestContext.getRequestParameters().get(RequestParameter.GENDER);

        User user = new User.Builder()
                .setUserLogin(login)
                .setUserEmail(email)
                .setUserFirstName(firstname)
                .setUserLastName(lastname)
                .setUserGender(true)
                .setUserDateOfBirth(new Date())
                .build();
        if (service.register(user, password)) {
            return new CommandResult(ResponseType.FORWARD, PagePath.LOGIN, new HashMap<>());
        }
        return new CommandResult(ResponseType.REDIRECT, PagePath.REGISTRATION, new HashMap<>());
    }
}
