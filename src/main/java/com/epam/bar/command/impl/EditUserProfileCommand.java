package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.entity.User;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.UserService;
import com.epam.bar.util.LocalizationMessage;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class EditUserProfileCommand implements Command {
    private final UserService service;

    public EditUserProfileCommand(UserService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        User user = (User) requestContext.getSessionAttributes().get(RequestParameter.USER);
        String firstName = requestContext.getRequestParameters().get(RequestParameter.FIRST_NAME);
        String lastName = requestContext.getRequestParameters().get(RequestParameter.LAST_NAME);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        CommandResult commandResult = new CommandResult(new HashMap<>(), new HashMap<>());
        try {
            Optional<String> serverMessage = service.updateUser(user);
            if (serverMessage.isPresent()) {
                commandResult = new CommandResult(Map.of(RequestAttribute.SERVER_MESSAGE,
                        LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get())),
                        new HashMap<>());
            }
        } catch (ServiceException e) {
            log.error("Update user failed" + e);
            commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                    CommandType.ERROR.getCommandName()), new HashMap<>());
        }
        return commandResult;
    }
}