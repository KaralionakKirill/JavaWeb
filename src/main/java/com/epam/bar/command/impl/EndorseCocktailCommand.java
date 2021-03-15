package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.CocktailService;
import com.epam.bar.util.LocalizationMessage;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class EndorseCocktailCommand implements Command {
    private final CocktailService service;

    public EndorseCocktailCommand(CocktailService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String id = requestContext.getRequestParameters().get(RequestParameter.COCKTAIL_ID);
        CommandResult commandResult = new CommandResult(new HashMap<>(), new HashMap<>());
        try {
            Optional<String> serverMessage = service.endorseCocktail(id);
            if (serverMessage.isPresent()) {
                commandResult = new CommandResult(
                        Map.of(RequestAttribute.SERVER_MESSAGE, LocalizationMessage.localize(requestContext.getLocale(),
                                serverMessage.get())), new HashMap<>());
            }
        } catch (ServiceException e) {
            log.error("Endorse cocktail failed" + e);
            commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                    CommandType.ERROR.getCommandName()), new HashMap<>());
        }
        return commandResult;
    }
}
