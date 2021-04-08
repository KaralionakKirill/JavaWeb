package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.entity.Review;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.CocktailService;
import com.epam.bar.service.ReviewService;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Moving the user to a cocktail page
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class ToCocktailViewCommand implements Command {
    private final CocktailService cocktailService;
    private final ReviewService reviewService;

    /**
     * @param cocktailService the cocktail service
     * @param reviewService   the review service
     */
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
            commandResult = cocktailOptional
                    .map(cocktail -> new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD,
                            PagePath.COCKTAIL_VIEW), Map.of(RequestAttribute.REVIEWS, reviews),
                            Map.of(RequestAttribute.COCKTAIL, cocktail)))
                    .orElseGet(() -> new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD,
                            PagePath.COCKTAILS)));
        } catch (ServiceException e) {
            log.error(e);
            commandResult = new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.ERROR_PAGE));
        }
        return commandResult;
    }
}
