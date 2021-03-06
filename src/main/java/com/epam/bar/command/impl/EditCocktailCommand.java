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
public class EditCocktailCommand implements Command {
    private final CocktailService service;

    public EditCocktailCommand(CocktailService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String id = requestContext.getRequestParameters().get(RequestParameter.COCKTAIL_ID);
        String name = requestContext.getRequestParameters().get(RequestParameter.COCKTAIL_NAME);
        String composition = requestContext.getRequestParameters().get(RequestParameter.COMPOSITION);
        CommandResult commandResult;
        try {
            Optional<String> serverMessage = service.editCocktail(id, name, composition);
            if (serverMessage.isEmpty()) {
                commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                        CommandType.TO_ALL_COCKTAILS.getCommandName()), new HashMap<>());
            } else {
                commandResult = new CommandResult(
                        Map.of(RequestAttribute.SERVER_MESSAGE, LocalizationMessage.localize(requestContext.getLocale(),
                                serverMessage.get())), new HashMap<>());
            }
        } catch (ServiceException e) {
            log.error(e);
            commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                    CommandType.ERROR.getCommandName()), new HashMap<>());
        }
        return commandResult;
    }
}
