package com.epam.JavaWeb.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder(setterPrefix = "with")
public class User extends Entity {
    private String login;
    private String email;
    private String firstName;
    private String lastName;

    public User() {
    }

    public User(String login, String email, String firstName, String lastName) {
        this.login = login;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
