package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.entity.Role;
import com.epam.bar.entity.User;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.UserService;
import com.epam.bar.util.LocalizationMessage;
import com.epam.bar.validator.ChainValidator;
import com.epam.bar.validator.impl.EmailValidator;
import com.epam.bar.validator.impl.PasswordRepeatValidator;
import com.epam.bar.validator.impl.PasswordValidator;
import com.epam.bar.validator.impl.UsernameValidator;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Registers the {@link User} and send activation link
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class RestRegistrationCommand implements Command {
    private final UserService service;

    /**
     * @param service the service
     */
    public RestRegistrationCommand(UserService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        CommandResult commandResult;
        String login = requestContext.getRequestParameters().get(RequestParameter.LOGIN);
        String password = requestContext.getRequestParameters().get(RequestParameter.PASSWORD);
        String repeatPassword = requestContext.getRequestParameters().get(RequestParameter.REPEAT_PASSWORD);
        String email = requestContext.getRequestParameters().get(RequestParameter.EMAIL);
        ChainValidator validator = new UsernameValidator(login, new EmailValidator(email, new PasswordValidator(
                password, new PasswordRepeatValidator(password, repeatPassword))));
        Optional<String> serverMessage = validator.validate();
        if (serverMessage.isEmpty()) {
            User user = User.builder()
                    .withRole(Role.USER)
                    .withLogin(login)
                    .withEmail(email)
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
