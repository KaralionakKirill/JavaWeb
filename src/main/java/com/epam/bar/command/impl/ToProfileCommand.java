package com.epam.bar.command.impl;

import com.epam.bar.command.*;
import com.epam.bar.command.marker.UserCommandMarker;

/**
 * Moving the user to a profile
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class ToProfileCommand implements Command, UserCommandMarker {

    @Override
    public CommandResult execute(RequestContext requestContext) {
        return new CommandResult(new ForwardResponse(PagePath.PROFILE));
    }
}
