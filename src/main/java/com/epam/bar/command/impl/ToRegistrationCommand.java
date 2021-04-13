package com.epam.bar.command.impl;

import com.epam.bar.command.*;

/**
 * Moving the user to a registration page
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class ToRegistrationCommand implements Command {
    @Override
    public CommandResult execute(RequestContext requestContext) {
        return new CommandResult(new ForwardResponse(PagePath.REGISTRATION));
    }
}
