package com.epam.bar.entity;

public enum Alcohol {
    VODKA(1),
    WHISKEY(2),
    TEQUILA(3),
    GIN(4),
    RUM(5);

    private int id;

    Alcohol(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
