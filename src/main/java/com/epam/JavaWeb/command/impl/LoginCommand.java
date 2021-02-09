package com.epam.JavaWeb.command.impl;

import com.epam.JavaWeb.command.*;
import com.epam.JavaWeb.exception.ServiceException;
import com.epam.JavaWeb.service.UserService;
import com.epam.JavaWeb.util.LocalizationMessage;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class LoginCommand implements Command {
    private final UserService service;

    public LoginCommand(UserService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String email = requestContext.getRequestParameters().get(RequestParameter.EMAIL);
        String password = requestContext.getRequestParameters().get(RequestParameter.PASSWORD);
        CommandResult commandResult;
        try {
            Optional<String> serverMessage = service.login(email, password);
            if (serverMessage.isEmpty()) {
                commandResult = new CommandResult(ResponseType.REDIRECT, PagePath.MAIN, new HashMap<>());
            } else {
                commandResult = new CommandResult(ResponseType.FORWARD, PagePath.LOGIN,
                        Map.of(RequestAttribute.SERVER_MESSAGE,
                                LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get())));
            }
        } catch (ServiceException e) {
            log.error("Login failed" + e);
            commandResult = new CommandResult(ResponseType.FORWARD,
                    PagePath.ERROR_PAGE);
        }
        return commandResult;
    }
}
