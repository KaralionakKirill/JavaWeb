package com.epam.bar.command.impl;

import com.epam.bar.command.*;

import java.util.HashMap;

public class ToAddCocktailCommand implements Command, UserCommand {
    @Override
    public CommandResult execute(RequestContext requestContext) {
        return new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.ADD_COCKTAIL),
                new HashMap<>(), new HashMap<>());
    }
}
