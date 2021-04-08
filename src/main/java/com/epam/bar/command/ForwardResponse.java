package com.epam.bar.command;

/**
 * Returns in the {@link CommandResult} to make a {@link com.epam.bar.controller.Controller} do a http forward
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public class ForwardResponse extends ResponseContext {
    private final String page;

    /**
     * Instantiates a new Forward response.
     *
     * @param type the type
     * @param page the page
     */
    public ForwardResponse(ResponseType type, String page) {
        super(type);
        this.page = page;
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }
}
