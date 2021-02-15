package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.entity.User;
import com.epam.bar.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToProfileCommand implements Command {
    private final UserService service;

    public ToProfileCommand(UserService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        List<User> users = service.findAllUsers();
        return new CommandResult(new ForwardResponse(ResponseType.FORWARD, PagePath.PROFILE), Map.of("users", users),
                new HashMap<>());
    }
}
