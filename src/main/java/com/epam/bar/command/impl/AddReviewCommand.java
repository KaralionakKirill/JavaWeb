package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.entity.Review;
import com.epam.bar.entity.User;
import com.epam.bar.exception.ServiceException;
import com.epam.bar.service.ReviewService;
import com.epam.bar.util.LocalizationMessage;
import com.epam.bar.validator.Validator;
import com.epam.bar.validator.impl.FeedbackValidator;
import com.epam.bar.validator.impl.RateValidator;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class AddReviewCommand implements Command, UserCommand {
    private final ReviewService service;

    public AddReviewCommand(ReviewService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        CommandResult commandResult;
        String feedback = requestContext.getRequestParameters().get(RequestParameter.FEEDBACK);
        int rate = Integer.parseInt(requestContext.getRequestParameters().get(RequestParameter.RATE));
        Validator validator = new RateValidator(rate, new FeedbackValidator(feedback));
        Optional<String> serverMessage = validator.validate();
        if(serverMessage.isEmpty()) {
            User user = (User) requestContext.getSessionAttributes().get(RequestParameter.USER);
            Cocktail cocktail = (Cocktail) requestContext.getSessionAttributes().get(RequestParameter.COCKTAIL);
            Review review = Review.builder()
                    .withFeedback(feedback)
                    .withCocktail(cocktail)
                    .withRate(rate)
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
        }else{
            commandResult = new CommandResult(Map.of(RequestAttribute.SERVER_MESSAGE,
                    LocalizationMessage.localize(requestContext.getLocale(), serverMessage.get())), new HashMap<>());
        }
        return commandResult;
    }
}
