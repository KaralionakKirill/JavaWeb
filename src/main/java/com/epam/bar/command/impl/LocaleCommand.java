package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

/**
 * Change locale
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class LocaleCommand implements Command {

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String locale = requestContext.getRequestParameters().get(RequestParameter.LOCALE);
        String url = requestContext.getRequestParameters().get(RequestParameter.CURRENT_URL);
        return new CommandResult(new RedirectResponse(url),
                new HashMap<>(), Map.of(RequestParameter.LOCALE, locale));
    }
}
