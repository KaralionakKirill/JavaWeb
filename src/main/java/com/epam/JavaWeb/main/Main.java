package com.epam.JavaWeb.main;

import com.epam.JavaWeb.db.ConnectorCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = ConnectorCreator.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(login, password, email) VALUES(?,?,?)");
            preparedStatement.setString(1, "kirill");
            preparedStatement.setString(2,"231201");
            preparedStatement.setString(3,"karalionak_kirill@mail.ru");
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM users");
            while (resultSet.next()){
                System.out.println(resultSet.getString(2));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

}
