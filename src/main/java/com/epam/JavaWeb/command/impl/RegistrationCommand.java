package com.epam.JavaWeb.command.impl;

import com.epam.JavaWeb.command.*;

import java.util.HashMap;

public class RegistrationCommand implements Command {
    @Override
    public CommandResult execute(RequestContext requestContext) {
        return new CommandResult(ResponseType.FORWARD, PagePath.REGISTRATION, new HashMap<>());
    }
}
