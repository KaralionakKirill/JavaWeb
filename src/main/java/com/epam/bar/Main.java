package com.epam.bar;

import com.epam.bar.entity.User;
import com.epam.bar.util.PasswordEncoder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        String pass ="Kirik_2343464";
        pass = PasswordEncoder.encryption(pass);
        System.out.println(pass);
    }

}
