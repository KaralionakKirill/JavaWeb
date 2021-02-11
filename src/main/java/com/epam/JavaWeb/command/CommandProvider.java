package com.epam.JavaWeb.command;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

@Log4j2
public class CommandProvider {

    public static Optional<Command> defineCommand(String commandName) {
        Optional<Command> current;
        if (commandName == null && commandName.isBlank()) {
            return Optional.empty();
        }
        try {
            CommandType type = CommandType.valueOf(commandName.toUpperCase());
            current = Optional.of(type.getCommand());
        } catch (IllegalArgumentException e) {
            log.error(e);
            current = Optional.empty();
        }
        return current;
    }
}
