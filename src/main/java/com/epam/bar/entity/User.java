package com.epam.bar.entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder(setterPrefix = "with")
public class User extends Entity{
    private Long id;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private Integer loyaltyPoints;
    private String activationCode;
    private boolean activated;

    public enum Role {
        ADMIN(1),
        USER(2),
        BARMAN(3);

        private int id;

        Role(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
