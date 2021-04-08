package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.command.marker.BarmanCommandMarker;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.CocktailService;
import com.epam.bar.util.LocalizationMessage;
import com.epam.bar.validator.ChainValidator;
import com.epam.bar.validator.impl.CocktailNameValidator;
import com.epam.bar.validator.impl.CompositionValidator;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Changing the {@link com.epam.bar.entity.Cocktail} description
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class RestEditCocktailCommand implements Command, BarmanCommandMarker {
    private final static String CONFIRMATION_MESSAGE = "serverMessage.editCocktailSuccess";
    private final CocktailService service;

    /**
     * @param service the service
     */
    public RestEditCocktailCommand(CocktailService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        CommandResult commandResult;
        String id = requestContext.getRequestParameters().get(RequestParameter.COCKTAIL_ID);
        String name = requestContext.getRequestParameters().get(RequestParameter.COCKTAIL_NAME);
        String composition = requestContext.getRequestParameters().get(RequestParameter.COMPOSITION);
        ChainValidator validator = new CocktailNameValidator(name, new CompositionValidator(composition));
        Optional<String> serverMessage = validator.validate();
        if (serverMessage.isEmpty()) {
            try {
                serverMessage = service.editCocktail(id, name, composition);
                if (serverMessage.isEmpty()) {
                    commandResult = new CommandResult(Map.of(RequestAttribute.CONFIRMATION_MESSAGE,
                            LocalizationMessage.localize(requestContext.getLocale(), CONFIRMATION_MESSAGE),
                            RequestAttribute.REDIRECT_COMMAND, CommandType.TO_ALL_COCKTAILS.getCommandName()), new HashMap<>());
                } else {
                    commandResult = new CommandResult(Map.of(RequestAttribute.SERVER_MESSAGE,
                            LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get())), new HashMap<>());
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
