package com.epam.JavaWeb.command.impl;

import com.epam.JavaWeb.command.*;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class LocaleCommand implements Command {

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String locale = requestContext.getRequestParameters().get(RequestParameter.LOCALE);
        String lastUrl = requestContext.getRequestParameters().get(RequestParameter.CURRENT_PAGE);
        return new CommandResult(new RedirectResponse(ResponseType.REDIRECT, lastUrl),
                new HashMap<>(), Map.of(RequestParameter.LOCALE, locale));
    }
}
