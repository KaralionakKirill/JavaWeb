package com.epam.bar.command.impl;

import com.epam.bar.command.*;

import java.util.HashMap;

public class ToRegistrationCommand implements Command {
    @Override
    public CommandResult execute(RequestContext requestContext) {
        return new CommandResult(new ForwardResponse(ResponseType.FORWARD, PagePath.REGISTRATION), new HashMap<>(), new HashMap<>());
    }
}
