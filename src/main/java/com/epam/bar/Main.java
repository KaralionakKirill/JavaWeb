package com.epam.bar;

import com.epam.bar.entity.Role;
import com.epam.bar.util.PasswordEncoder;

public class Main {

    public static void main(String[] args) {
       String password ="Kirik_2343464";
        System.out.println(PasswordEncoder.encryption(password));
    }

}
