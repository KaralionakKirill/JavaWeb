package com.epam.bar.command;

public class RedirectResponse extends ResponseContext{
    private final String command;
    public RedirectResponse(ResponseType type, String command) {
        super(type);
        this.command = command;
    }

    public String getCommand() {
        return command.startsWith("?") ? command : "?" + RequestParameter.COMMAND + "=" + command;
    }
}
