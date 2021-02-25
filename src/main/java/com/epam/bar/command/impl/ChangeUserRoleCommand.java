package com.epam.bar.command.impl;

import com.epam.bar.command.Command;
import com.epam.bar.command.CommandResult;
import com.epam.bar.command.RequestContext;
import com.epam.bar.command.RequestParameter;
import com.epam.bar.entity.Role;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.UserService;

import java.util.HashMap;
import java.util.Optional;

public class ChangeUserRoleCommand implements Command {
    private final UserService userService;

    public ChangeUserRoleCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        int userId = Integer.parseInt(requestContext.getRequestParameters().get(RequestParameter.ID));
        Role role = Role.valueOf(requestContext.getRequestParameters().get(RequestParameter.ROLE));
        try {
            Optional<String> serverMessage = userService.changeUserRole(userId, role);
            if(serverMessage.isEmpty()){
                System.out.println("this work");
            }
        } catch (ServiceException e) {
            e.printStackTrace();//todo
        }
        return new CommandResult(new HashMap<>(), new HashMap<>());
    }
}
