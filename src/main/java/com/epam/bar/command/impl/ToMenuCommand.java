package com.epam.bar.command.impl;

import com.epam.bar.command.*;

public class ToMenuCommand implements Command {
    @Override
    public CommandResult execute(RequestContext requestContext) {
        return new CommandResult(new ForwardResponse(ResponseType.FORWARD, PagePath.MENU));
    }
}
