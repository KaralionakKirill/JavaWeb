package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.entity.User;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.UserService;
import com.epam.bar.util.LocalizationMessage;
import com.epam.bar.validator.ChainValidator;
import com.epam.bar.validator.impl.EmailValidator;
import com.epam.bar.validator.impl.PasswordValidator;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Authorizes the {@link User} and saves him in the {@link javax.servlet.http.HttpSession}
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class RestLoginCommand implements Command {
    private final UserService service;

    /**
     * @param service the service
     */
    public RestLoginCommand(UserService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        CommandResult commandResult;
        String email = requestContext.getRequestParameters().get(RequestParameter.EMAIL);
        String password = requestContext.getRequestParameters().get(RequestParameter.PASSWORD);
        ChainValidator validator = new EmailValidator(email, new PasswordValidator(password));
        Optional<String> serverMessage = validator.validate();
        if (serverMessage.isEmpty()) {
            User user = User.builder()
                    .withEmail(email)
                    .build();
            try {
                serverMessage = service.login(user, password);
                if (serverMessage.isEmpty()) {
                    commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                            CommandType.TO_MAIN.getCommandName()), Map.of(RequestAttribute.USER, user));
                } else {
                    commandResult = new CommandResult(
                            Map.of(RequestAttribute.SERVER_MESSAGE, LocalizationMessage.localize(requestContext.getLocale(),
                                    serverMessage.get())), new HashMap<>());
                }
            } catch (ServiceException e) {
                log.error("Login failed" + e);
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
