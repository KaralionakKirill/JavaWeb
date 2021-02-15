package com.epam.bar.command;

public class ForwardResponse extends ResponseContext{
    private final String page;
    public ForwardResponse(ResponseType type, String page) {
        super(type);
        this.page = page;
    }

    public String getPage() {
        return page;
    }
}
