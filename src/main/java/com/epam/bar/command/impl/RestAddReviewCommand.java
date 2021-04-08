package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.command.marker.UserCommandMarker;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.entity.Review;
import com.epam.bar.entity.User;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.ReviewService;
import com.epam.bar.util.LocalizationMessage;
import com.epam.bar.validator.ChainValidator;
import com.epam.bar.validator.impl.FeedbackValidator;
import com.epam.bar.validator.impl.NumberValidator;
import com.epam.bar.validator.impl.RateValidator;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Review of the {@link Cocktail} from the {@link User}
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class RestAddReviewCommand implements Command, UserCommandMarker {
    private final ReviewService service;

    /**
     * @param service the service
     */
    public RestAddReviewCommand(ReviewService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        CommandResult commandResult;
        String feedback = requestContext.getRequestParameters().get(RequestParameter.FEEDBACK);
        String rate = requestContext.getRequestParameters().get(RequestParameter.RATE);
        ChainValidator validator = new NumberValidator(rate,
                new RateValidator(Integer.parseInt(rate),
                        new FeedbackValidator(feedback)));
        Optional<String> serverMessage = validator.validate();
        if (serverMessage.isEmpty()) {
            User user = (User) requestContext.getSessionAttributes().get(RequestParameter.USER);
            Cocktail cocktail = (Cocktail) requestContext.getSessionAttributes().get(RequestParameter.COCKTAIL);
            Review review = Review.builder()
                    .withFeedback(feedback)
                    .withCocktail(cocktail)
                    .withRate(Integer.parseInt(rate))
                    .withAuthor(user)
                    .build();
            try {
                service.addReview(review);
                commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                        CommandType.TO_COCKTAIL_VIEW.getCommandName(), RequestAttribute.COCKTAIL_ID, cocktail.getId()),
                        new HashMap<>());
            } catch (ServiceException e) {
                log.error("Review add failed" + e);
                commandResult = new CommandResult(Map.of(RequestAttribute.REDIRECT_COMMAND,
                        CommandType.ERROR.getCommandName()), new HashMap<>());
            }
        } else {
            commandResult = new CommandResult(Map.of(RequestAttribute.SERVER_MESSAGE,
                    LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get())), new HashMap<>());
        }
        return commandResult;
    }
}
