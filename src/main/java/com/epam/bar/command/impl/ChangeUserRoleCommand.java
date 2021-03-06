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
public class ChangeUserRoleCommand implements Command {
    private final UserService userService;

    public ChangeUserRoleCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        Long userId = Long.getLong(requestContext.getRequestParameters().get(RequestParameter.USER_ID));
        User.Role role = User.Role.valueOf(requestContext.getRequestParameters().get(RequestParameter.ROLE));
        CommandResult commandResult = new CommandResult(new HashMap<>(), new HashMap<>());
        try {
            Optional<String> serverMessage = userService.changeUserRole(userId, role);
            if (serverMessage.isPresent()) {
                commandResult = new CommandResult(Map.of(RequestAttribute.SERVER_MESSAGE,
                        LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get())),
                        new HashMap<>());
            }
        } catch (ServiceException e) {
            log.error("Change role failed" + e);
            commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                    CommandType.ERROR.getCommandName()), new HashMap<>());
        }
        return commandResult;
    }
}