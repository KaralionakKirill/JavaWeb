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
     * @param page the page
     */
    public ForwardResponse(String page) {
        super(ResponseType.FORWARD);
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
