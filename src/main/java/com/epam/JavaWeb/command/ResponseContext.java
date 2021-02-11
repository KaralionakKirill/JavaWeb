package com.epam.JavaWeb.command;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class ResponseContext {
    private final ResponseType type;

    public ResponseContext(ResponseType type) {
        this.type = type;
    }

    public ResponseType getType() {
        return type;
    }
}
