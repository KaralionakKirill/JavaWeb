package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.entity.Alcohol;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.CocktailService;
import com.epam.bar.util.LocalizationMessage;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Log4j2
public class AddCocktailCommand implements Command {
    private final CocktailService service;

    public AddCocktailCommand(CocktailService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        Part img = requestContext.getRequestParts().get(RequestParameter.IMAGE);
        String filename = UUID.randomUUID() + img.getSubmittedFileName();
        int authorId = Integer.parseInt(requestContext.getRequestParameters().get(RequestParameter.AUTHOR_ID));
        String name = requestContext.getRequestParameters().get(RequestParameter.COCKTAIL_NAME);
        String alcohol = requestContext.getRequestParameters().get(RequestParameter.ALCOHOL);
        String composition = requestContext.getRequestParameters().get(RequestParameter.COMPOSITION);
        Cocktail cocktail = Cocktail.builder()
                .withAuthorId(authorId)
                .withName(name)
                .withComposition(composition)
                .withAlcohol(Alcohol.valueOf(alcohol))
                .withImgName(filename)
                .withIsApproved(false)
                .build();
        CommandResult commandResult;
        try {
            Optional<String> serverMessage = service.addCocktail(cocktail);
            if (serverMessage.isEmpty()) {
                img.write(filename);
                commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                        CommandType.TO_ADD_COCKTAIL.getCommandName()),
                        new HashMap<>());
            } else {
                commandResult = new CommandResult(new ForwardResponse(ResponseType.FORWARD, PagePath.REGISTRATION),
                        Map.of(RequestAttribute.SERVER_MESSAGE,
                                LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get())));
            }
        } catch (ServiceException | IOException e) {
            log.error("Add cocktail failed" + e);
            commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                    CommandType.ERROR.getCommandName()), new HashMap<>());
        }

        return commandResult;
    }
}