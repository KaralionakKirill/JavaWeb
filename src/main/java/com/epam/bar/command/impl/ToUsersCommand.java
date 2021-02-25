package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.entity.User;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.UserService;
import com.epam.bar.util.PageContent;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class ToUsersCommand implements Command {
    private final UserService service;

    public ToUsersCommand(UserService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        CommandResult commandResult;
        int page = Integer.parseInt(requestContext.getRequestParameters().get(RequestParameter.PAGE));
        try {
            List<User> users = service.findAllUsers();
            if(users.size() != 0){
                PageContent<User> pageContent = new PageContent<>(users, page);
                commandResult = new CommandResult(new ForwardResponse(ResponseType.FORWARD, PagePath.USERS),
                        Map.of(RequestAttribute.PAGE_CONTENT, pageContent), new HashMap<>());
            }else{
                commandResult = new CommandResult(new ForwardResponse(ResponseType.FORWARD, PagePath.USERS),
                        new HashMap<>(), new HashMap<>());
            }
        } catch (ServiceException e) {
            log.error(e);
            commandResult = new CommandResult(new ForwardResponse(ResponseType.FORWARD, PagePath.ERROR_PAGE),
                    new HashMap<>(), new HashMap<>());
        }
        return commandResult;
    }
}
