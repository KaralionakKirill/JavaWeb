package com.epam.bar.entity;

/**
 * The enum Role.
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
public enum Role {
    /**
     * Admin role.
     */
    ADMIN(1),
    /**
     * User role.
     */
    USER(2),
    /**
     * Barman role.
     */
    BARMAN(3),
    /**
     * Guest role.
     */
    GUEST(4);

    private final int id;

    Role(int id) {
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
