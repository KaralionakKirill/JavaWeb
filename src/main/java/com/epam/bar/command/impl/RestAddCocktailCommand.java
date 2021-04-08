package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.command.marker.UserCommandMarker;
import com.epam.bar.entity.Alcohol;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.entity.Role;
import com.epam.bar.entity.User;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.CocktailService;
import com.epam.bar.util.LocalizationMessage;
import com.epam.bar.validator.ChainValidator;
import com.epam.bar.validator.impl.CocktailNameValidator;
import com.epam.bar.validator.impl.CompositionValidator;
import com.epam.bar.validator.impl.ImgFileValidator;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Adding a cocktail by the {@link User}
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class RestAddCocktailCommand implements Command, UserCommandMarker {
    private final static String ACCEPT_MESSAGE = "acceptanceMessage";
    private final CocktailService service;

    /**
     * @param service the service
     */
    public RestAddCocktailCommand(CocktailService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        CommandResult commandResult;
        Part img = requestContext.getRequestParts().get(RequestParameter.IMAGE);
        String filename = UUID.randomUUID() + img.getSubmittedFileName();
        User author = (User) requestContext.getSessionAttributes().get(RequestParameter.USER);
        String name = requestContext.getRequestParameters().get(RequestParameter.COCKTAIL_NAME);
        String alcohol = requestContext.getRequestParameters().get(RequestParameter.ALCOHOL);
        String composition = requestContext.getRequestParameters().get(RequestParameter.COMPOSITION);
        Role role = author.getRole();
        ChainValidator validator = new ImgFileValidator(img, new CocktailNameValidator(name,
                new CompositionValidator(composition)));
        Optional<String> serverMessage = validator.validate();
        if (serverMessage.isEmpty()) {
            Cocktail cocktail = Cocktail.builder()
                    .withAuthor(author)
                    .withName(name)
                    .withComposition(composition)
                    .withAlcohol(Alcohol.valueOf(alcohol))
                    .withImgName(filename)
                    .withApproved(role != Role.USER)
                    .build();
            try {
                serverMessage = service.addCocktail(cocktail);
                if (serverMessage.isEmpty()) {
                    img.write(filename);
                    commandResult = new CommandResult(Map.of(RequestAttribute.CONFIRMATION_MESSAGE,
                            LocalizationMessage.localize(requestContext.getLocale(), ACCEPT_MESSAGE),
                            RequestAttribute.REDIRECT_COMMAND, CommandType.TO_ADD_COCKTAIL.getCommandName()),
                            new HashMap<>());
                } else {
                    commandResult = new CommandResult(Map.of(RequestAttribute.SERVER_MESSAGE,
                            LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get()),
                            RequestAttribute.REDIRECT_COMMAND, CommandType.TO_ADD_COCKTAIL.getCommandName()),
                            new HashMap<>());
                }
            } catch (ServiceException | IOException e) {
                log.error("Add cocktail failed" + e);
                commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                        CommandType.ERROR.getCommandName()), new HashMap<>());
            }
        } else {
            commandResult = new CommandResult(Map.of(RequestAttribute.SERVER_MESSAGE,
                    LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get()),
                    RequestAttribute.REDIRECT_COMMAND, CommandType.TO_ADD_COCKTAIL.getCommandName()),
                    new HashMap<>());
        }
        return commandResult;
    }
}
