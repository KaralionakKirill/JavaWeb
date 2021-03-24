package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.CocktailService;
import com.epam.bar.util.LocalizationMessage;
import com.epam.bar.validator.Validator;
import com.epam.bar.validator.impl.CocktailNameValidator;
import com.epam.bar.validator.impl.CompositionValidator;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class EditCocktailCommand implements Command, BarmanCommand {
    private final CocktailService service;

    public EditCocktailCommand(CocktailService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        CommandResult commandResult;
        String id = requestContext.getRequestParameters().get(RequestParameter.COCKTAIL_ID);
        String name = requestContext.getRequestParameters().get(RequestParameter.COCKTAIL_NAME);
        String composition = requestContext.getRequestParameters().get(RequestParameter.COMPOSITION);
        Validator validator = new CocktailNameValidator(name, new CompositionValidator(composition));
        Optional<String> serverMessage = validator.validate();
        if (serverMessage.isEmpty()) {
            try {
                serverMessage = service.editCocktail(id, name, composition);
                if (serverMessage.isEmpty()) {
                    commandResult = new CommandResult(Map.of(RequestAttribute.CONFIRMATION_MESSAGE,
                            LocalizationMessage.localize(requestContext.getLocale(), "serverMessage.editCocktailSuccess"),
                            RequestAttribute.REDIRECT_COMMAND, CommandType.TO_ALL_COCKTAILS.getCommandName()), new HashMap<>());
                } else {
                    commandResult = new CommandResult(
                            Map.of(RequestAttribute.SERVER_MESSAGE, LocalizationMessage.localize(requestContext.getLocale(),
                                    serverMessage.get())), new HashMap<>());
                }
            } catch (ServiceException e) {
                log.error("Edit cocktail failed" + e);
                commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                        CommandType.ERROR.getCommandName()), new HashMap<>());
            }
        } else {
            commandResult = new CommandResult(
                    Map.of(RequestAttribute.SERVER_MESSAGE, LocalizationMessage.localize(requestContext.getLocale(),
                            serverMessage.get())), new HashMap<>());
        }
        return commandResult;
    }
}
