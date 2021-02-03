package com.epam.JavaWeb.command.impl;

import com.epam.JavaWeb.command.*;
import com.epam.JavaWeb.entity.User;
import com.epam.JavaWeb.exception.DaoException;
import com.epam.JavaWeb.service.UserService;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;

@Log4j2
public class RegistrationCommand implements Command {
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
        String email = requestContext.getRequestParameters().get(RequestParameter.EMAIL);

        User user = User.builder()
                .withLogin(login)
                .withEmail(email)
                .withFirstName(firstname)
                .withLastName(lastname)
                .build();
        try {
            if (service.register(user, password)) {
                return new CommandResult(ResponseType.FORWARD, PagePath.LOGIN, new HashMap<>());
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return new CommandResult(ResponseType.REDIRECT, PagePath.REGISTRATION, new HashMap<>());
    }
}
