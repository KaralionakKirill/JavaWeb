package com.epam.bar.command.impl;

import com.epam.bar.command.*;

import java.util.HashMap;

public class ToMenuCommand implements Command {
    @Override
    public CommandResult execute(RequestContext requestContext) {
        requestContext.getSessionAttributes().put(RequestParameter.COCKTAIL, null);
        return new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.MENU), new HashMap<>(),
                requestContext.getSessionAttributes());
    }
}
