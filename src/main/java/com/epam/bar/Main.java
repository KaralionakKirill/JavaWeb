package com.epam.bar;

import com.epam.bar.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        User user = User.builder()
                .withFirstName("Kirill")
                .withLogin("kira")
                .withId(30L)
                .build();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(user);
        System.out.println(json);
    }

}
