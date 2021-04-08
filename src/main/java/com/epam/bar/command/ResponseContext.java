package com.epam.bar.command;

import lombok.EqualsAndHashCode;

/**
 * Indicates the type of the {@link CommandResult}
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@EqualsAndHashCode
public abstract class ResponseContext {
    /**
     * The enum Response type.
     */
    public enum ResponseType {
        /**
         * Forward response type.
         */
        FORWARD,
        /**
         * Redirect response type.
         */
        REDIRECT
    }

    private final ResponseType type;

    /**
     * Instantiates a new Response context.
     *
     * @param type the type
     */
    public ResponseContext(ResponseType type) {
        this.type = type;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public ResponseType getType() {
        return type;
    }
}
