package com.epam.bar.command;

public interface Command {
    CommandResult execute(RequestContext requestContext);
}
