package com.epam.bar.command;

import com.epam.bar.command.impl.*;
import com.epam.bar.service.UserService;

public enum CommandType {
    VERIFICATION(new ActivateUserCommand(new UserService()), "verification"),
    LOGIN(new LoginCommand(new UserService()), "login"),
    LOCALE(new LocaleCommand(), "locale"),
    REGISTRATION(new RegistrationCommand(new UserService()), "registration"),
    TO_LOGIN(new ToLoginCommand(),"to_login"),
    TO_REGISTRATION(new ToRegistrationCommand(),"to_registration"),
    TO_MAIN(new ToMainCommand(),"to_main"),
    TO_REGISTRATION_CONFIRM(new ToRegistrationConfirmCommand(),"to_registration_confirm"),
    TO_LOGOUT(new LogoutCommand(),"to_logout"),
    TO_PROFILE(new ToProfileCommand(new UserService()),"to_profile");

    private Command command;
    private String commandName;

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
