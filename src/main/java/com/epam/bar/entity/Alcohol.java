package com.epam.bar.entity;

/**
 * The enum Alcohol.
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public enum Alcohol {
    /**
     * Vodka alcohol.
     */
    VODKA(1),
    /**
     * Whiskey alcohol.
     */
    WHISKEY(2),
    /**
     * Tequila alcohol.
     */
    TEQUILA(3),
    /**
     * Gin alcohol.
     */
    GIN(4),
    /**
     * Rum alcohol.
     */
    RUM(5),
    /**
     * Champagne alcohol.
     */
    CHAMPAGNE(6);

    private final int id;

    Alcohol(int id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }
}
