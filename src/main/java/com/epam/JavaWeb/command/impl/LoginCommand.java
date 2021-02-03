package com.epam.JavaWeb.command.impl;

import com.epam.JavaWeb.command.*;
import com.epam.JavaWeb.exception.ServiceException;
import com.epam.JavaWeb.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private final UserService service;

    public LoginCommand(UserService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String email = requestContext.getRequestParameters().get(RequestParameter.EMAIL);
        String password = requestContext.getRequestParameters().get(RequestParameter.PASSWORD);
        CommandResult commandResult = null;
        try {
            if (service.login(email, password)) {
                commandResult = new CommandResult(ResponseType.FORWARD, PagePath.LOGIN, new HashMap<>());
            }else {
                commandResult = new CommandResult(ResponseType.FORWARD, PagePath.REGISTRATION, new HashMap<>());
            }
        }catch (ServiceException e){
            logger.info(e);
        }
        return commandResult;
    }
}
