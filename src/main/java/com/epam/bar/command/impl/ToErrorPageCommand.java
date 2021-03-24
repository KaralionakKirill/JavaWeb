package com.epam.bar.command.impl;

import com.epam.bar.command.*;

import java.util.HashMap;

public class ToErrorPageCommand implements Command {

    @Override
    public CommandResult execute(RequestContext requestContext) {
        return new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.ERROR_PAGE), new HashMap<>(),
                new HashMap<>());
    }
}
