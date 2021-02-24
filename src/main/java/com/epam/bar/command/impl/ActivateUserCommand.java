package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.UserService;
import com.epam.bar.util.LocalizationMessage;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class ActivateUserCommand implements Command {
    private final UserService service;

    public ActivateUserCommand(UserService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        CommandResult commandResult;
        String activationCode = requestContext.getRequestParameters().get(RequestParameter.VERIFICATION_CODE);
        try {
            Optional<String> serverMessage = service.activateUser(activationCode);
            if (serverMessage.isEmpty()) {
                commandResult = new CommandResult(
                        new ForwardResponse(ResponseType.FORWARD, PagePath.LOGIN),
                        Map.of(RequestAttribute.VERIFICATION_MESSAGE, LocalizationMessage.localize(
                                requestContext.getLocale(), "verificationMessage.verificationSuccess")),
                        new HashMap<>());
            }else {
                commandResult = new CommandResult(new ForwardResponse(ResponseType.FORWARD, PagePath.LOGIN),
                        Map.of(RequestAttribute.SERVER_MESSAGE, LocalizationMessage.localize(
                                        requestContext.getLocale(), serverMessage.get())), new HashMap<>());
            }
        } catch (ServiceException e) {
            log.error("Failed to activate user", e);
            commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                    CommandType.ERROR.getCommandName()), new HashMap<>());
        }
        return commandResult;
    }
}
