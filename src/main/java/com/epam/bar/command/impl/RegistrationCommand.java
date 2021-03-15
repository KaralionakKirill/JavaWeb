package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.entity.User;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.UserService;
import com.epam.bar.util.LocalizationMessage;
import com.epam.bar.validator.impl.EmailValidator;
import com.epam.bar.validator.impl.PasswordRepeatValidator;
import com.epam.bar.validator.impl.PasswordValidator;
import com.epam.bar.validator.impl.UsernameValidator;
import lombok.extern.log4j.Log4j2;

import java.util.Collections;
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
        CommandResult commandResult;
        Optional<String> serverMessage =
                new UsernameValidator(
                        new EmailValidator(
                                new PasswordValidator(
                                        new PasswordRepeatValidator(null))))
                        .validate(requestContext.getRequestParameters());
        if (serverMessage.isEmpty()) {
            String login = requestContext.getRequestParameters().get(RequestParameter.LOGIN);
            String password = requestContext.getRequestParameters().get(RequestParameter.PASSWORD);
            String email = requestContext.getRequestParameters().get(RequestParameter.EMAIL);
            User user = User.builder()
                    .withRole(User.Role.USER)
                    .withLogin(login)
                    .withEmail(email)
                    .withActivated(false)
                    .withLoyaltyPoints(0)
                    .build();
            try {
                serverMessage = service.register(user, password);
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
        } else {
            commandResult = new CommandResult(
                    Map.of(RequestAttribute.SERVER_MESSAGE, LocalizationMessage.localize(requestContext.getLocale(),
                            serverMessage.get())), new HashMap<>());
        }
        return commandResult;
    }
}
