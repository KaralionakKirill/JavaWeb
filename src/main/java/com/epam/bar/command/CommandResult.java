package com.epam.bar.command;

import java.util.HashMap;
import java.util.Map;

/**
 * The result of the {@link Command} work
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class CommandResult {

    private final Map<String, Object> attributes;
    private final Map<String, Object> sessionAttributes;
    private ResponseContext responseContext;

    /**
     * Instantiates a new Command result.
     *
     * @param attributes        the attributes
     * @param sessionAttributes the session attributes
     */
    public CommandResult(Map<String, Object> attributes, Map<String, Object> sessionAttributes) {
        this.attributes = attributes;
        this.sessionAttributes = sessionAttributes;
    }

    /**
     * Instantiates a new Command result.
     *
     * @param responseContext   the response context
     * @param attributes        the attributes
     * @param sessionAttributes the session attributes
     */
    public CommandResult(ResponseContext responseContext, Map<String, Object> attributes,
                         Map<String, Object> sessionAttributes) {
        this.responseContext = responseContext;
        this.attributes = attributes;
        this.sessionAttributes = sessionAttributes;
    }

    /**
     * Instantiates a new Command result.
     *
     * @param responseContext the response context
     * @param attributes      the attributes
     */
    public CommandResult(ResponseContext responseContext, Map<String, Object> attributes) {
        this.responseContext = responseContext;
        this.attributes = attributes;
        this.sessionAttributes = new HashMap<>();
    }

    /**
     * Instantiates a new Command result.
     *
     * @param responseContext the response context
     */
    public CommandResult(ResponseContext responseContext) {
        this.responseContext = responseContext;
        this.attributes = new HashMap<>();
        this.sessionAttributes = new HashMap<>();
    }

    /**
     * Gets response context.
     *
     * @return the response context
     */
    public ResponseContext getResponseContext() {
        return responseContext;
    }

    /**
     * Gets attributes.
     *
     * @return the attributes
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * Gets session attributes.
     *
     * @return the session attributes
     */
    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }
}
