package com.epam.bar.command.impl;

import com.epam.bar.command.*;

import java.util.Map;

/**
 * Logout user
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class LogoutCommand implements Command {
    @Override
    public CommandResult execute(RequestContext requestContext) {
        return new CommandResult(new ForwardResponse(PagePath.MAIN),
                Map.of(RequestAttribute.REMOVE, RequestAttribute.USER));
    }
}
