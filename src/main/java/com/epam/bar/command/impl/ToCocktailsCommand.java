package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.dao.CocktailField;
import com.epam.bar.entity.Alcohol;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.CocktailService;
import com.epam.bar.validator.ChainValidator;
import com.epam.bar.validator.impl.AlcoholValidator;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Moving the user to a page with cocktails with a certain alcohol
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class ToCocktailsCommand implements Command {
    private final CocktailService service;

    /**
     * @param service the service
     */
    public ToCocktailsCommand(CocktailService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        CommandResult commandResult;
        String alcoholName = requestContext.getRequestParameters().get(RequestParameter.ALCOHOL);
        ChainValidator validator = new AlcoholValidator(alcoholName);
        Optional<String> serverMessage = validator.validate();
        if (serverMessage.isEmpty()) {
            Alcohol alcohol = Alcohol.valueOf(alcoholName);
            try {
                String alcoholId = Integer.toString(alcohol.getId());
                List<Cocktail> cocktails = service.findByField(alcoholId, CocktailField.ALCOHOL);
                cocktails = cocktails.stream().filter(Cocktail::isApproved).collect(Collectors.toList());
                if (cocktails.size() != 0) {
                    commandResult = new CommandResult(new ForwardResponse(PagePath.COCKTAILS),
                            Map.of(RequestAttribute.COCKTAILS, cocktails));
                } else {
                    commandResult = new CommandResult(new ForwardResponse(PagePath.MENU));
                }
            } catch (ServiceException e) {
                log.error(e);
                commandResult = new CommandResult(new ForwardResponse(PagePath.ERROR_PAGE));
            }
        } else {
            commandResult = new CommandResult(new ForwardResponse(PagePath.ERROR_PAGE));
        }
        return commandResult;
    }
}
