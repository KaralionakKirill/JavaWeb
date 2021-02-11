package com.epam.JavaWeb.command.impl;

import com.epam.JavaWeb.command.*;

import java.util.HashMap;

public class ToMainCommand implements Command {
    @Override
    public CommandResult execute(RequestContext requestContext) {
        return new CommandResult(new ForwardResponse(ResponseType.FORWARD, PagePath.MAIN), new HashMap<>(), new HashMap<>());
    }
}
