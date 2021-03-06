package com.epam.bar.command.impl;

import com.epam.bar.command.Command;
import com.epam.bar.command.CommandResult;
import com.epam.bar.command.RequestContext;
import com.epam.bar.command.RequestParameter;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.CocktailService;

import java.util.HashMap;
import java.util.Optional;

public class EndorseCocktailCommand implements Command {
    private final CocktailService service;

    public EndorseCocktailCommand(CocktailService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String id =  requestContext.getRequestParameters().get(RequestParameter.COCKTAIL_ID);
        try {
            Optional<String> serverMessage = service.endorseCocktail(id);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return new CommandResult(new HashMap<>(), new HashMap<>());
    }
}
