package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.command.marker.UserCommandMarker;

/**
 * Moves an user to cocktail adding page
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class ToAddCocktailCommand implements Command, UserCommandMarker {
    @Override
    public CommandResult execute(RequestContext requestContext) {
        return new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.ADD_COCKTAIL));
    }
}
