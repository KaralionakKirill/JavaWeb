package com.epam.bar.command;

import com.epam.bar.command.impl.*;
import com.epam.bar.service.CocktailService;
import com.epam.bar.service.ReviewService;
import com.epam.bar.service.UserService;

public enum CommandType {
    VERIFICATION(new ActivateUserCommand(new UserService()), "verification"),
    LOGIN(new LoginCommand(new UserService()), "login"),
    LOCALE(new LocaleCommand(), "locale"),
    REGISTRATION(new RegistrationCommand(new UserService()), "registration"),
    ADD_COCKTAIL(new AddCocktailCommand(new CocktailService()), "add_cocktail"),
    ERROR(new ToErrorPageCommand(), "error"),
    UPDATE_USER(new UpdateUserCommand(new UserService()),"update_user"),
    ADD_REVIEW(new AddReviewCommand(new ReviewService()),"add_review"),
    USER_EDIT_PROFILE(new EditUserProfileCommand(new UserService()),"user_edit_profile"),
    EDIT_COCKTAIL(new EditCocktailCommand(new CocktailService()),"edit_cocktail"),
    ENDORSE_COCKTAIL(new EndorseCocktailCommand(new CocktailService()),"endorse_cocktail"),
    DELETE_COCKTAIL(new DeleteCocktailCommand(new CocktailService()),"delete_cocktail"),
    TO_COCKTAIL_VIEW(new ToCocktailViewCommand(new CocktailService(), new ReviewService()),"to_cocktail_view"),
    TO_LOGIN(new ToLoginCommand(),"to_login"),
    TO_REGISTRATION(new ToRegistrationCommand(),"to_registration"),
    TO_MAIN(new ToMainCommand(),"to_main"),
    TO_REGISTRATION_CONFIRM(new ToRegistrationConfirmCommand(),"to_registration_confirm"),
    TO_LOGOUT(new LogoutCommand(),"to_logout"),
    TO_ADD_COCKTAIL(new ToAddCocktailCommand(),"to_add_cocktail"),
    TO_PROFILE(new ToProfileCommand(),"to_profile"),
    TO_COCKTAILS(new ToCocktailsCommand(new CocktailService()),"to_cocktails"),
    TO_ALL_COCKTAILS(new ToAllCocktailsCommand(new CocktailService()),"to_all_cocktails"),
    TO_MENU(new ToMenuCommand(), "to_menu"),
    TO_USERS(new ToUsersCommand(new UserService()), "to_users");

    private final Command command;
    private final String commandName;

    CommandType(Command command, String commandName) {
        this.command = command;
        this.commandName = commandName;
    }

    public Command getCommand() {
        return command;
    }

    public String getCommandName() {
        return commandName;
    }
}
