package com.epam.bar.command.impl;

import com.epam.bar.command.*;

/**
 * Moving the user to a error page
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class ToErrorPageCommand implements Command {

    @Override
    public CommandResult execute(RequestContext requestContext) {
        return new CommandResult(new ForwardResponse(ResponseContext.ResponseType.FORWARD, PagePath.ERROR_PAGE));
    }
}
