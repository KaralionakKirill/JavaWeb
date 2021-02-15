package com.epam.bar.command;

import java.util.HashMap;
import java.util.Map;

public class CommandResult {

    private final Map<String, Object> attributes;
    private final Map<String, Object> sessionAttributes;
    private ResponseContext responseContext;

    public CommandResult(Map<String, Object> attributes, Map<String, Object> sessionAttributes) {
        this.attributes = attributes;
        this.sessionAttributes = sessionAttributes;
    }

    public CommandResult(ResponseContext responseContext, Map<String, Object> attributes,
                         Map<String, Object> sessionAttributes) {
        this.responseContext = responseContext;
        this.attributes = attributes;
        this.sessionAttributes = sessionAttributes;
    }

    public CommandResult(ResponseContext responseContext, Map<String, Object> attributes) {
        this.responseContext = responseContext;
        this.attributes = attributes;
        this.sessionAttributes = new HashMap<>();
    }

    public CommandResult(ResponseContext responseContext) {
        this.responseContext = responseContext;
        this.attributes = new HashMap<>();
        this.sessionAttributes = new HashMap<>();
    }

    public ResponseContext getResponseContext() {
        return responseContext;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }
}
