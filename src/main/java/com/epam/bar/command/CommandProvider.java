package com.epam.bar.command;

import lombok.extern.log4j.Log4j2;

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
