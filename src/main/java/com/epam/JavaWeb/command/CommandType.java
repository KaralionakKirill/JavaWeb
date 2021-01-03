package com.epam.JavaWeb.command;

import com.epam.JavaWeb.command.impl.LoginCommand;
import com.epam.JavaWeb.command.impl.RegistrationCommand;
import com.epam.JavaWeb.service.UserService;

public enum CommandType {
    LOGIN(new LoginCommand(new UserService())),
    REGISTRATION(new RegistrationCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
