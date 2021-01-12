package com.epam.JavaWeb.command;

public interface Command {
    CommandResult execute(RequestContext requestContext);
}
