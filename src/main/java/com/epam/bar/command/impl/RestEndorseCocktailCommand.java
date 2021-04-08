package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.command.marker.BarmanCommandMarker;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.CocktailService;
import com.epam.bar.util.LocalizationMessage;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Cocktail approval from the bartender{@link com.epam.bar.entity.User}
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class RestEndorseCocktailCommand implements Command, BarmanCommandMarker {
    private final static String CONFIRMATION_MESSAGE = "serverMessage.endorseCocktailSuccess";
    private final CocktailService service;

    /**
     * @param service the service
     */
    public RestEndorseCocktailCommand(CocktailService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String id = requestContext.getRequestParameters().get(RequestParameter.COCKTAIL_ID);
        CommandResult commandResult;
        try {
            Optional<String> serverMessage = service.endorseCocktail(id);
            if (serverMessage.isEmpty()) {
                commandResult = new CommandResult(Map.of(RequestAttribute.CONFIRMATION_MESSAGE,
                        LocalizationMessage.localize(requestContext.getLocale(), CONFIRMATION_MESSAGE),
                        RequestAttribute.REDIRECT_COMMAND, CommandType.TO_ALL_COCKTAILS.getCommandName()), new HashMap<>());
            } else {
                commandResult = new CommandResult(Map.of(RequestAttribute.SERVER_MESSAGE,
                        LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get())), new HashMap<>());
            }
        } catch (ServiceException e) {
            log.error("Endorse cocktail failed" + e);
            commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                    CommandType.ERROR.getCommandName()), new HashMap<>());
        }
        return commandResult;
    }
}
