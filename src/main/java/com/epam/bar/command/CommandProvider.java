package com.epam.bar.command;

import lombok.extern.log4j.Log4j2;

import java.util.Optional;

/**
 * Defines a {@link Command} by name
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@Log4j2
public class CommandProvider {

    /**
     * Define command optional.
     *
     * @param commandName the command name
     * @return the optional with command or empty optional
     */
    public static Optional<Command> defineCommand(String commandName) {
        Optional<Command> current;
        if (commandName == null) {
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
