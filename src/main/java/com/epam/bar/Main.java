package com.epam.bar;

import com.epam.bar.db.ConnectionPool;
import com.epam.bar.entity.Alcohol;
import com.epam.bar.entity.Cocktail;
import com.epam.bar.entity.Role;
import com.epam.bar.exception.DaoException;
import com.epam.bar.util.PasswordEncoder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        List<Cocktail> cocktails = new ArrayList<>();
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT cocktail_name, alcohol_name, composition, img_name, is_approved  " +
                     "FROM cocktail INNER JOIN alcohol on cocktail.alcohol_id = alcohol.id " +
                     "WHERE alcohol_id = ?")) {
            preparedStatement.setInt(1, 2);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Cocktail cocktail = Cocktail.builder()
                        .withName(resultSet.getString("cocktail_name"))
                        .withAlcohol(Alcohol.valueOf(resultSet.getString("alcohol_name")))
                        .withComposition(resultSet.getString("composition"))
                        .withImgName(resultSet.getString("img_name"))
                        .withIsApproved(resultSet.getBoolean("is_approved"))
                        .build();
                cocktails.add(cocktail);
            }
        } catch (SQLException e) {
            System.out.println("Dont work");
        }
        System.out.println(cocktails.toString());
    }

}
