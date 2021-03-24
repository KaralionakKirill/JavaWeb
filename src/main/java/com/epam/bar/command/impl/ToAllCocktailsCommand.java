package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.CocktailService;
import com.epam.bar.util.PageContent;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class ToAllCocktailsCommand implements Command, BarmanCommand {
    private final CocktailService service;

    public ToAllCocktailsCommand(CocktailService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        CommandResult commandResult;
        int page = Integer.parseInt(requestContext.getRequestParameters().get(RequestParameter.PAGE));
        try {
            List<Cocktail> cocktails = service.findAll();
            if(cocktails.size() != 0){
                PageContent<Cocktail> pageContent = new PageContent<>(cocktails, page);
                commandResult = new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.COCKTAILS_LIST),
                        Map.of(RequestAttribute.PAGE_CONTENT, pageContent), new HashMap<>());
            }else{
                commandResult = new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.COCKTAILS_LIST),
                        new HashMap<>(), new HashMap<>());
            }
        } catch (ServiceException e) {
            log.error(e);
            commandResult = new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.ERROR_PAGE),
                    new HashMap<>(), new HashMap<>());
        }
        return commandResult;
    }
}
