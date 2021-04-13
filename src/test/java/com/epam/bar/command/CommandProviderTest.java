package com.epam.bar.command;

import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.assertEquals;

public class CommandProviderTest {

    @Test
    public void defineCommandShouldReturnEmptyOptional_WhenCommandNameIsNull() {
        final Optional<Command> expected = Optional.empty();
        final Optional<Command> actual = CommandProvider.defineCommand(null);
        assertEquals(actual, expected);
    }

    @Test
    public void defineCommandShouldReturnEmptyOptional_WhenCommandIsNotPresent() {
        final Optional<Command> expected = Optional.empty();
        final Optional<Command> actual = CommandProvider.defineCommand("testCommand");
        assertEquals(actual, expected);
    }

    @Test
    public void defineCommandShouldReturnEmptyOptional() {
        final Optional<Command> expected = Optional.of(CommandType.LOGIN.getCommand());
        final Optional<Command> actual = CommandProvider.defineCommand("login");
        assertEquals(actual, expected);
    }
}