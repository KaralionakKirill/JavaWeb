package com.epam.JavaWeb.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectorCreator {
    private static final String DB_PROPERTIES_PATH = "property/database.properties";
    private static final String DB_URL  = "db.url";
    private static final String DB_DRIVER = "db.driver";
    private static final Properties properties = new Properties();
    private static final String DATABASE_URL;

    static {
        try {
            ClassLoader classLoader = ConnectorCreator.class.getClassLoader();
            properties.load(classLoader.getResourceAsStream(DB_PROPERTIES_PATH));
            String driverName = (String) properties.get(DB_DRIVER);
            Class.forName(driverName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        DATABASE_URL = (String) properties.get(DB_URL);
    }

    private ConnectorCreator() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, properties);
    }
}
