package com.epam.bar.validator;

import com.epam.bar.validator.impl.EmailValidator;
import com.epam.bar.validator.impl.PasswordValidator;
import com.epam.bar.validator.impl.UsernameValidator;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import static org.testng.Assert.assertEquals;

public class ChainValidatorTest {

    @Test
    public void validatorShouldReturnEmptyMessage(){
      ChainValidator validator = new UsernameValidator("login",
              new EmailValidator("test@mail.ru",
                      new PasswordValidator("Password_isTest1")));
        Optional<String> expected = Optional.empty();
        Optional<String> actual = validator.validate();
        assertEquals(actual, expected);
    }

    @Test
    public void validatorShouldReturnServerMessage(){
        ChainValidator validator = new UsernameValidator("login",
                new EmailValidator("test@mail.ru",
                        new PasswordValidator("   test")));
        Optional<String> expected = Optional.of("serverMessage.invalid.password");
        Optional<String> actual = validator.validate();
        assertEquals(actual, expected);
    }

}