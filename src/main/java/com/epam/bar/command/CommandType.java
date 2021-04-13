package com.epam.bar.command;

import com.epam.bar.command.impl.*;
import com.epam.bar.dao.CocktailDao;
import com.epam.bar.dao.ReviewDao;
import com.epam.bar.dao.UserDao;
import com.epam.bar.service.CocktailService;
import com.epam.bar.service.ReviewService;
import com.epam.bar.service.UserService;

/**
 * Type of {@link Command}
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public enum CommandType {
    /**
     * The Verification.
     */
    VERIFICATION(new ActivateUserCommand(new UserService(new UserDao())), "verification"),
    /**
     * The Login.
     */
    LOGIN(new RestLoginCommand(new UserService(new UserDao())), "login"),
    /**
     * The Locale.
     */
    LOCALE(new LocaleCommand(), "locale"),
    /**
     * The Registration.
     */
    REGISTRATION(new RestRegistrationCommand(new UserService(new UserDao())), "registration"),
    /**
     * The Add cocktail.
     */
    ADD_COCKTAIL(new RestAddCocktailCommand(new CocktailService(new CocktailDao())), "add_cocktail"),
    /**
     * The Error.
     */
    ERROR(new ToErrorPageCommand(), "error"),
    /**
     * The Update user.
     */
    UPDATE_USER(new RestUpdateUserCommand(new UserService(new UserDao())), "update_user"),
    /**
     * The Add review.
     */
    ADD_REVIEW(new RestAddReviewCommand(new ReviewService(new ReviewDao())), "add_review"),
    /**
     * The User edit profile.
     */
    USER_EDIT_PROFILE(new RestEditUserProfileCommand(new UserService(new UserDao())), "user_edit_profile"),
    /**
     * The Edit cocktail.
     */
    EDIT_COCKTAIL(new RestEditCocktailCommand(new CocktailService(new CocktailDao())), "edit_cocktail"),
    /**
     * The Endorse cocktail.
     */
    ENDORSE_COCKTAIL(new RestEndorseCocktailCommand(new CocktailService(new CocktailDao())), "endorse_cocktail"),
    /**
     * The Delete cocktail.
     */
    DELETE_COCKTAIL(new RestDeleteCocktailCommand(new CocktailService(new CocktailDao())), "delete_cocktail"),
    /**
     * The To cocktail view.
     */
    TO_COCKTAIL_VIEW(new ToCocktailViewCommand(new CocktailService(new CocktailDao()),
            new ReviewService(new ReviewDao())), "to_cocktail_view"),
    /**
     * The To login.
     */
    TO_LOGIN(new ToLoginCommand(), "to_login"),
    /**
     * The To registration.
     */
    TO_REGISTRATION(new ToRegistrationCommand(), "to_registration"),
    /**
     * The To main.
     */
    TO_MAIN(new ToMainCommand(), "to_main"),
    /**
     * The To registration confirm.
     */
    TO_REGISTRATION_CONFIRM(new ToRegistrationConfirmCommand(), "to_registration_confirm"),
    /**
     * The To logout.
     */
    TO_LOGOUT(new LogoutCommand(), "to_logout"),
    /**
     * The To add cocktail.
     */
    TO_ADD_COCKTAIL(new ToAddCocktailCommand(), "to_add_cocktail"),
    /**
     * The To profile.
     */
    TO_PROFILE(new ToProfileCommand(), "to_profile"),
    /**
     * The To cocktails.
     */
    TO_COCKTAILS(new ToCocktailsCommand(new CocktailService(new CocktailDao())), "to_cocktails"),
    /**
     * The To all cocktails.
     */
    TO_ALL_COCKTAILS(new ToAllCocktailsCommand(new CocktailService(new CocktailDao())), "to_all_cocktails"),
    /**
     * The To menu.
     */
    TO_MENU(new ToMenuCommand(), "to_menu"),
    /**
     * The To users.
     */
    TO_USERS(new ToUsersCommand(new UserService(new UserDao())), "to_users");

    private final Command command;
    private final String commandName;

    CommandType(Command command, String commandName) {
        this.command = command;
        this.commandName = commandName;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }

    /**
     * Gets command name.
     *
     * @return the command name
     */
    public String getCommandName() {
        return commandName;
    }
}
