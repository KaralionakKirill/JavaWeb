package com.epam.bar.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The representation of User
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder(setterPrefix = "with")
public class User extends Entity {
    private Long id;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private Integer loyaltyPoints;
    private String activationCode;
    private boolean activated;
    private boolean blocked;

}
