package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.entity.User;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.UserService;
import com.epam.bar.util.LocalizationMessage;
import com.epam.bar.util.PasswordEncoder;
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
                .withRole(User.Role.USER)
                .withLogin(login)
                .withEmail(email)
                .withIsBlocked(false)
                .withIsActivate(false)
                .withLoyaltyPoints(0)
                .build();
        CommandResult commandResult;
        try {
            Optional<String> serverMessage = service.register(user, password);
            if (serverMessage.isEmpty()) {
                commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                        CommandType.TO_REGISTRATION_CONFIRM.getCommandName()),
                        new HashMap<>());
            } else {
                commandResult = new CommandResult(Map.of(RequestAttribute.SERVER_MESSAGE,
                        LocalizationMessage.localize(requestContext.getLocale(),
                                serverMessage.get())), new HashMap<>());
            }
        } catch (ServiceException e) {
            log.error("Registration failed" + e);
            commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                    CommandType.ERROR.getCommandName()), new HashMap<>());
        }
        return commandResult;
    }
}
