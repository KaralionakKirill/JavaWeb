package com.epam.bar.command.impl;

import com.epam.bar.command.*;

import java.util.HashMap;

public class ToProfileCommand implements Command, UserCommand {

    @Override
    public CommandResult execute(RequestContext requestContext) {
        return  new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.PROFILE),
                new HashMap<>(), new HashMap<>());
    }
}
