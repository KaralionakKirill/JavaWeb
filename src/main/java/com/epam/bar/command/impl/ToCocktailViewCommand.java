package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.entity.Review;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.CocktailService;
import com.epam.bar.service.ReviewService;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class ToCocktailViewCommand implements Command {
    private final CocktailService cocktailService;
    private final ReviewService reviewService;

    public ToCocktailViewCommand(CocktailService cocktailService, ReviewService reviewService) {
        this.cocktailService = cocktailService;
        this.reviewService = reviewService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String id = requestContext.getRequestParameters().get(RequestParameter.COCKTAIL_ID);
        CommandResult commandResult;
        try {
            Optional<Cocktail> cocktailOptional = cocktailService.findCocktailById(id);
            List<Review> reviews = reviewService.findReviewsByCocktailId(id);
            commandResult = cocktailOptional.map(cocktail -> new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD,
                    PagePath.COCKTAIL_VIEW), Map.of(RequestAttribute.REVIEWS, reviews),
                    Map.of(RequestAttribute.COCKTAIL, cocktail))).
                    orElseGet(() -> new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.COCKTAILS)));
        } catch (ServiceException e) {
            log.error(e);
            commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                    CommandType.ERROR.getCommandName()), new HashMap<>());
        }
        return commandResult;
    }
}
