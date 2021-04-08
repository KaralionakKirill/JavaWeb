package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.command.marker.AdminCommandMarker;
import com.epam.bar.entity.User;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.UserService;
import com.epam.bar.util.PageContent;
import com.epam.bar.validator.ChainValidator;
import com.epam.bar.validator.impl.NumberValidator;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Moving the user to a page with a list of all users
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class ToUsersCommand implements Command, AdminCommandMarker {
    private final UserService service;

    /**
     * @param service the service
     */
    public ToUsersCommand(UserService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        CommandResult commandResult;
        String page = requestContext.getRequestParameters().get(RequestParameter.PAGE);
        ChainValidator validator = new NumberValidator(page);
        Optional<String> serverMessage = validator.validate();
        if (serverMessage.isEmpty()) {
            try {
                List<User> users = service.findAllUsers();
                if (users.size() != 0) {
                    PageContent<User> pageContent = new PageContent<>(users, Integer.parseInt(page));
                    commandResult = new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.USERS),
                            Map.of(RequestAttribute.PAGE_CONTENT, pageContent));
                } else {
                    commandResult = new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.USERS));
                }
            } catch (ServiceException e) {
                log.error(e);
                commandResult = new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.ERROR_PAGE));
            }
        } else {
            commandResult = new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.ERROR_PAGE));
        }
        return commandResult;
    }
}
