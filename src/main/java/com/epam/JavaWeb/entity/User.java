package com.epam.JavaWeb.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends Entity {
    private String email;
    private String password;
    private boolean admin;
    private String firstName;
    private String lastName;

    public static class Builder {
        private User user;

        public Builder() {
            user = new User();
        }

        public Builder setUserEmail(String email) {
            this.user.setEmail(email);
            return this;
        }

        public Builder setUserPassword(String password) {
            this.user.setPassword(password);
            return this;
        }

        public Builder setUserFirstName(String firstName) {
            this.user.setFirstName(firstName);
            return this;
        }

        public Builder setUserLastName(String lastName) {
            this.user.setLastName(lastName);
            return this;
        }

        public Builder setUserAdmin(boolean admin) {
            this.user.setAdmin(admin);
            return this;
        }

        public User build() {
            return this.user;
        }
    }
}
