package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.dao.field.UserField;
import com.epam.bar.entity.Role;
import com.epam.bar.entity.User;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.UserService;
import com.epam.bar.util.LocalizationMessage;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class UpdateUserCommand implements Command, AdminCommand {
    private final UserService service;

    public UpdateUserCommand(UserService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String id = requestContext.getRequestParameters().get(RequestParameter.USER_ID);
        Role role = Role.valueOf(requestContext.getRequestParameters().get(RequestParameter.ROLE));
        boolean isBlocked = requestContext.getRequestParameters().get(RequestParameter.BLOCK).equals(RequestParameter.TRUE);
        CommandResult commandResult;
        try {
            Optional<User> userOptional = service.findByField(id, UserField.ID);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                user.setRole(role);
                user.setBlocked(isBlocked);
                Optional<String> serverMessage = service.updateUser(user);
                if (serverMessage.isEmpty()) {
                    commandResult = new CommandResult(Map.of(RequestAttribute.CONFIRMATION_MESSAGE,
                            LocalizationMessage.localize(requestContext.getLocale(), "serverMessage.updateUserSuccess"),
                            RequestAttribute.REDIRECT_COMMAND, CommandType.TO_USERS.getCommandName()),
                            new HashMap<>());
                }else{
                    commandResult = new CommandResult(Map.of(RequestAttribute.SERVER_MESSAGE,
                            LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get())),
                            new HashMap<>());
                }
            }else{
                commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                        CommandType.ERROR.getCommandName()), new HashMap<>());
            }
        } catch (ServiceException e) {
            log.error("Update user failed" + e);
            commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                    CommandType.ERROR.getCommandName()), new HashMap<>());
        }
        return commandResult;
    }
}
