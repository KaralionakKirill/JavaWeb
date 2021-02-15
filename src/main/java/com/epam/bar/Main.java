package com.epam.bar;

import com.epam.bar.entity.Role;
import com.epam.bar.entity.User;

public class Main {

    public static void change(User user) throws CloneNotSupportedException {
        User user1 = User.builder()
                .withLogin("Kirill")
                .withRole(Role.USER)
                .build();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        User user = User.builder()
                .withLogin("Nothing")
                .build();
        System.out.println(user);
        change(user);
        System.out.println(user);
    }

}
