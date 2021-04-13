package com.epam.bar.command;

/**
 * Returns in the {@link CommandResult} to make a {@link com.epam.bar.controller.Controller} do a http redirect
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class RedirectResponse extends ResponseContext {
    private final String command;

    /**
     * Instantiates a new Redirect response.
     *
     * @param command the command
     */
    public RedirectResponse(String command) {
        super(ResponseType.REDIRECT);
        this.command = command;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public String getCommand() {
        return command.startsWith("?") ? command : "?" + RequestParameter.COMMAND + "=" + command;
    }
}
