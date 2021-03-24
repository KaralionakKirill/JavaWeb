package com.epam.bar.entity;

public enum Role {
    ADMIN(1),
    USER(2),
    BARMAN(3),
    GUEST(4);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
