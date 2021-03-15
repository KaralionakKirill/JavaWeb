package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.CocktailService;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class ToCocktailViewCommand implements Command {
    private final CocktailService service;

    public ToCocktailViewCommand(CocktailService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String id = requestContext.getRequestParameters().get(RequestParameter.COCKTAIL_ID);
        CommandResult commandResult;
        try {
            Optional<Cocktail> cocktail = service.findCocktailById(id);
            commandResult = cocktail.map(value -> new CommandResult(new ForwardResponse(ResponseType.FORWARD,
                    PagePath.COCKTAIL_VIEW), Map.of(RequestAttribute.COCKTAIL, value))).
                    orElseGet(() -> new CommandResult(new ForwardResponse(ResponseType.FORWARD, PagePath.COCKTAILS)));
        } catch (ServiceException e) {
            log.error(e);
            commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                    CommandType.ERROR.getCommandName()), new HashMap<>());
        }
        return commandResult;
    }
}
