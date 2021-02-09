package com.epam.JavaWeb.entity;

public enum Role {
    USER(1),
    ADMIN(2),
    BARMAN(3);

    private int id;

    Role(int id) {
        this.id = id;
    }
}
