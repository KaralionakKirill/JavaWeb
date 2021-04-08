package com.epam.bar.command;

/**
 * The interface processes the client's {@link RequestContext} using the Command pattern
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public interface Command {
    /**
     * Execute command result.
     *
     * @param requestContext the request context
     * @return the command result {@link CommandResult}
     */
    CommandResult execute(RequestContext requestContext);
}
