package com.epam.JavaWeb.command.impl;

import com.epam.JavaWeb.command.*;
import com.epam.JavaWeb.entity.Role;
import com.epam.JavaWeb.entity.User;
import com.epam.JavaWeb.exception.ServiceException;
import com.epam.JavaWeb.service.UserService;
import com.epam.JavaWeb.util.LocalizationMessage;
import com.epam.JavaWeb.util.PasswordEncoder;
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
        String email = requestContext.getRequestParameters().get(RequestParameter.EMAIL);
        password = PasswordEncoder.encryption(password);
        User user = User.builder()
                .withRole(Role.USER)
                .withLogin(login)
                .withEmail(email)
                .withIsBlocked(false)
                .withIsActivate(false)
                .build();
        CommandResult commandResult;
        try {
            Optional<String> serverMessage = service.register(user, password);
            if (serverMessage.isEmpty()) {
                commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                        CommandType.TO_LOGIN.getCommandName(), RequestAttribute.VERIFICATION_MESSAGE,
                        LocalizationMessage.localize(requestContext.getLocale(), "verificationMessage.verifyPlease")),
                        new HashMap<>());
            } else {
                commandResult = new CommandResult(new ForwardResponse(ResponseType.FORWARD, PagePath.REGISTRATION),
                        Map.of(RequestAttribute.SERVER_MESSAGE,
                                LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get())));
            }
        } catch (ServiceException e) {
            log.error("Registration failed" + e);
            commandResult = new CommandResult(new ForwardResponse(ResponseType.FORWARD,
                    PagePath.ERROR_PAGE));
        }
        return commandResult;
    }
}
