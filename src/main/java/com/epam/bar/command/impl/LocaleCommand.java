package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import lombok.extern.log4j.Log4j2;

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
