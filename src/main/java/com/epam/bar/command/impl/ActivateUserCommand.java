package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.UserService;
import com.epam.bar.util.LocalizationMessage;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The class activates the user{@link com.epam.bar.entity.User} account
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class ActivateUserCommand implements Command {
    private final UserService service;
    private final static String VERIFICATION_MESSAGE = "verificationMessage.verificationSuccess";

    /**
     * @param service the service
     */
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
                commandResult = new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.LOGIN),
                        Map.of(RequestAttribute.VERIFICATION_MESSAGE, LocalizationMessage.localize(
                                requestContext.getLocale(), VERIFICATION_MESSAGE)));
            } else {
                commandResult = new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.LOGIN),
                        Map.of(RequestAttribute.SERVER_MESSAGE, LocalizationMessage.localize(
                                requestContext.getLocale(), serverMessage.get())));
            }
        } catch (ServiceException e) {
            log.error("Failed to activate user", e);
            commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                    CommandType.ERROR.getCommandName()), new HashMap<>());
        }
        return commandResult;
    }
}
