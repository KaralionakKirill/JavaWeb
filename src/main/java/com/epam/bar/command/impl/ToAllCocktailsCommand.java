package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.command.marker.BarmanCommandMarker;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.CocktailService;
import com.epam.bar.util.PageContent;
import com.epam.bar.validator.ChainValidator;
import com.epam.bar.validator.impl.NumberValidator;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Moving the user to a page with a list of all cocktails
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class ToAllCocktailsCommand implements Command, BarmanCommandMarker {
    private final CocktailService service;

    /**
     * @param service the service
     */
    public ToAllCocktailsCommand(CocktailService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        CommandResult commandResult;
        String page = requestContext.getRequestParameters().get(RequestParameter.PAGE);
        ChainValidator validator = new NumberValidator(page);
        Optional<String> serverMessage = validator.validate();
        if (serverMessage.isEmpty()) {
            try {
                List<Cocktail> cocktails = service.findAll();
                if (cocktails.size() != 0) {
                    PageContent<Cocktail> pageContent = new PageContent<>(cocktails, Integer.parseInt(page));
                    commandResult = new CommandResult(new ForwardResponse(
                            ResponseContext.ResponseType.FORWARD, PagePath.COCKTAILS_LIST),
                            Map.of(RequestAttribute.PAGE_CONTENT, pageContent));
                } else {
                    commandResult = new CommandResult(new ForwardResponse(
                            ResponseContext.ResponseType.FORWARD, PagePath.COCKTAILS_LIST));
                }
            } catch (ServiceException e) {
                log.error(e);
                commandResult = new CommandResult(new ForwardResponse(
                        ResponseContext.ResponseType.FORWARD, PagePath.ERROR_PAGE));
            }
        } else {
            commandResult = new CommandResult(new ForwardResponse(
                    ResponseContext.ResponseType.FORWARD, PagePath.ERROR_PAGE));
        }
        return commandResult;
    }
}
