package com.epam.bar.command;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class ResponseContext {
    public enum ResponseType {
        FORWARD,
        REDIRECT
    }

    private final ResponseType type;

    public ResponseContext(ResponseType type) {
        this.type = type;
    }

    public ResponseType getType() {
        return type;
    }
}
