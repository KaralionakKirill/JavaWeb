package com.epam.bar.entity;

public enum Role {
    ADMIN(1),
    USER(2),
    BARMAN(3);

    private int id;

    Role(int  id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}