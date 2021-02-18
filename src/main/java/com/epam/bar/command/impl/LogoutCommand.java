package com.epam.bar.command.impl;

import com.epam.bar.command.*;

import java.util.HashMap;

public class LogoutCommand implements Command {
    @Override
    public CommandResult execute(RequestContext requestContext) {
        requestContext.getSessionAttributes().put(RequestAttribute.USER, null);
        return new CommandResult(new ForwardResponse(ResponseType.FORWARD, PagePath.MAIN),
                new HashMap<>(), requestContext.getSessionAttributes());
    }
}
