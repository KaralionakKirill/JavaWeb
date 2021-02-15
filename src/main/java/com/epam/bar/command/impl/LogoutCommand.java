package com.epam.bar.command.impl;

import com.epam.bar.command.*;

import java.util.HashMap;

public class LogoutCommand implements Command {
    @Override
    public CommandResult execute(RequestContext requestContext) {
        return new CommandResult(new RedirectResponse(ResponseType.REDIRECT, CommandType.TO_MAIN.getCommandName()),
                new HashMap<>(), new HashMap<>());
    }
}
