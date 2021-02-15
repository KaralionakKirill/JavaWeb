package com.epam.bar.entity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder(setterPrefix = "with")
public class User extends Entity{
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private Integer loyaltyPoints;
    private String activationCode;
    private boolean isActivate;
    private boolean isBlocked;
}
