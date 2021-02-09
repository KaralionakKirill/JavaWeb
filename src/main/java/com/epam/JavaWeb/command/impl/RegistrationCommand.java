package com.epam.JavaWeb.command.impl;

import com.epam.JavaWeb.command.*;
import com.epam.JavaWeb.entity.User;
import com.epam.JavaWeb.exception.DaoException;
import com.epam.JavaWeb.service.UserService;
import com.epam.JavaWeb.util.LocalizationMessage;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        CommandResult commandResult = null;
        try {
            Optional<String> serverMessage = service.register(user, password);
            if (serverMessage.isEmpty()) {
                commandResult = new CommandResult(ResponseType.REDIRECT, PagePath.LOGIN, new HashMap<>());
            } else {
                commandResult = new CommandResult(ResponseType.FORWARD, PagePath.REGISTRATION,
                        Map.of(RequestAttribute.SERVER_MESSAGE,
                                LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get())));
            }
        } catch (DaoException e) {
            log.error("Registration failed" + e);
            commandResult = new CommandResult(ResponseType.FORWARD,
                    PagePath.ERROR_PAGE);
        }
        return commandResult;
    }
}
