package com.epam.JavaWeb.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User extends Entity {
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private boolean gender;
    private Date dateOfBirth;

    public static class Builder {
        private final User user;

        public Builder() {
            user = new User();
        }

        public Builder setUserLogin(String login) {
            this.user.setLogin(login);
            return this;
        }

        public Builder setUserEmail(String email) {
            this.user.setEmail(email);
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

        public Builder setUserGender(boolean gender) {
            this.user.setGender(gender);
            return this;
        }

        public Builder setUserDateOfBirth(Date dateOfBirth) {
            this.user.setDateOfBirth(dateOfBirth);
            return this;
        }

        public User build() {
            return this.user;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return getLogin().equals(user.getLogin()) && getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        int result = 31;
        result = getLogin().hashCode() * result + result;
        result = getEmail().hashCode() * result + result;
        return result;
    }
}
