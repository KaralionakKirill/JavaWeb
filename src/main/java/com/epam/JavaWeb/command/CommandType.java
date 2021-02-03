package com.epam.JavaWeb.command;

import com.epam.JavaWeb.command.impl.SetLocaleCommand;
import com.epam.JavaWeb.command.impl.LoginCommand;
import com.epam.JavaWeb.command.impl.RegistrationCommand;
import com.epam.JavaWeb.command.impl.ToLoginCommand;
import com.epam.JavaWeb.service.UserService;

public enum CommandType {
    LOGIN(new LoginCommand(new UserService())),
    SET_LOCALE(new SetLocaleCommand()),
    REGISTRATION(new RegistrationCommand(new UserService())),
    TO_LOGIN(new ToLoginCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
