package com.epam.JavaWeb.command.impl;

import com.epam.JavaWeb.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SetLocaleCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SetLocaleCommand.class);

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String locale = requestContext.getRequestParameters().get(RequestParameter.LOCALE);
        String currCommand = requestContext.getRequestParameters().get(RequestParameter.CURRENT_PAGE);
        return new CommandResult(ResponseType.REDIRECT, currCommand, new HashMap<>(), Map.of(RequestParameter.LOCALE, locale));
    }
}
