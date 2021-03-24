package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.dao.field.CocktailField;
import com.epam.bar.entity.Alcohol;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.CocktailService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
public class ToCocktailsCommand implements Command {
    private final CocktailService service;

    public ToCocktailsCommand(CocktailService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        Alcohol alcohol = Alcohol.valueOf(requestContext.getRequestParameters().get(RequestParameter.ALCOHOL));
        CommandResult commandResult;
        try {
            String alcoholId = Integer.toString(alcohol.getId());
            List<Cocktail> cocktails = service.findByField(alcoholId, CocktailField.ALCOHOL);
            cocktails = cocktails.stream().filter(Cocktail::isApproved).collect(Collectors.toList());
            if (cocktails.size() != 0) {
                commandResult = new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.COCKTAILS),
                        Map.of(RequestAttribute.COCKTAILS, cocktails));
            } else {
                commandResult = new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.COCKTAILS));
            }
        } catch (ServiceException e) {
            log.error(e);
            commandResult = new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.ERROR_PAGE));
        }
        return commandResult;
    }
}
