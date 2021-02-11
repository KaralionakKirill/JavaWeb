package com.epam.JavaWeb.command;

import com.epam.JavaWeb.command.impl.*;
import com.epam.JavaWeb.service.UserService;

public enum CommandType {
    VERIFICATION(new ActivateUserCommand(new UserService()), "verification"),
    LOGIN(new LoginCommand(new UserService()), "login"),
    LOCALE(new LocaleCommand(), "locale"),
    REGISTRATION(new RegistrationCommand(new UserService()), "registration"),
    TO_LOGIN(new ToLoginCommand(),"to_login"),
    TO_REGISTRATION(new ToRegistrationCommand(),"to_registration"),
    TO_MAIN(new ToMainCommand(),"to_main");

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
