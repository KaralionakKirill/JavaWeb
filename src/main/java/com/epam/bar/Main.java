package com.epam.bar;

import com.epam.bar.command.AdminCommand;
import com.epam.bar.command.Command;
import com.epam.bar.command.CommandType;
import com.epam.bar.command.impl.LoginCommand;
import com.epam.bar.db.ConnectionPool;
import com.epam.bar.service.UserService;
import com.epam.bar.validator.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        LoginCommand loginCommand = new LoginCommand(new UserService());
        System.out.println(Arrays.toString(loginCommand.getClass().getInterfaces()));
        System.out.println(Command.class.isAssignableFrom(loginCommand.getClass()));
        System.out.println(AdminCommand.class.isInstance(loginCommand));
    }

}
