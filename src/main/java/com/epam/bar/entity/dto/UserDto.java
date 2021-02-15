package com.epam.bar.entity.dto;

import com.epam.bar.entity.Entity;
import com.epam.bar.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends Entity {
    private String login;
    private String email;
    private Role role;
    private boolean isActivate;
    private boolean isBlocked;
}
