package com.epam.bar.command.impl;

import com.epam.bar.command.*;

import java.util.Map;

/**
 * Moving the user to a menu page
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class ToMenuCommand implements Command {
    @Override
    public CommandResult execute(RequestContext requestContext) {
        return new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.MENU),
                Map.of(RequestAttribute.REMOVE, RequestAttribute.COCKTAIL));
    }
}
