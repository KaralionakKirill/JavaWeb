package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.command.marker.UserCommandMarker;
import com.epam.bar.entity.User;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.UserService;
import com.epam.bar.util.LocalizationMessage;
import com.epam.bar.validator.ChainValidator;
import com.epam.bar.validator.impl.NameValidator;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Changing the user's{@link User} first and last name
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class RestEditUserProfileCommand implements Command, UserCommandMarker {
    private final static String CONFIRMATION_MESSAGE = "serverMessage.editUserSuccess";
    private final UserService service;

    /**
     * @param service the service
     */
    public RestEditUserProfileCommand(UserService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        CommandResult commandResult;
        User user = (User) requestContext.getSessionAttributes().get(RequestParameter.USER);
        String firstName = requestContext.getRequestParameters().get(RequestParameter.FIRST_NAME);
        String lastName = requestContext.getRequestParameters().get(RequestParameter.LAST_NAME);
        ChainValidator validator = new NameValidator(firstName, lastName);
        Optional<String> serverMessage = validator.validate();
        if (serverMessage.isEmpty()) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            try {
                serverMessage = service.updateUser(user);
                if (serverMessage.isEmpty()) {
                    commandResult = new CommandResult(Map.of(RequestAttribute.CONFIRMATION_MESSAGE,
                            LocalizationMessage.localize(requestContext.getLocale(), CONFIRMATION_MESSAGE),
                            RequestAttribute.REDIRECT_COMMAND, CommandType.TO_PROFILE.getCommandName()),
                            new HashMap<>());
                } else {
                    commandResult = new CommandResult(Map.of(RequestAttribute.SERVER_MESSAGE,
                            LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get()),
                            RequestAttribute.REDIRECT_COMMAND, CommandType.TO_PROFILE.getCommandName()),
                            new HashMap<>());
                }
            } catch (ServiceException e) {
                log.error("Edit user failed" + e);
                commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                        CommandType.ERROR.getCommandName()), new HashMap<>());
            }
        } else {
            commandResult = new CommandResult(Map.of(RequestAttribute.SERVER_MESSAGE,
                    LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get()),
                    RequestAttribute.REDIRECT_COMMAND, CommandType.TO_PROFILE.getCommandName()),
                    new HashMap<>());
        }
        return commandResult;
    }
}
