package com.epam.JavaWeb.db;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.Properties;

@Log4j2
final class ConnectorCreator {
    private static final String DB_PROPERTIES_PATH = "database.properties";
    private static final String DB_URL = "db.url";
    private static final String DB_DRIVER = "db.driver";
    private static final Properties properties = new Properties();
    private static final String DATABASE_URL;

    static {
        try {
            ClassLoader classLoader = ConnectorCreator.class.getClassLoader();
            properties.load(classLoader.getResourceAsStream(DB_PROPERTIES_PATH));
            String driverName = (String) properties.get(DB_DRIVER);
            Class.forName(driverName);
            DATABASE_URL = (String) properties.get(DB_URL);
        } catch (IOException | ClassNotFoundException | MissingResourceException e) {
            log.fatal("fatal error: config file" + e);
            throw new RuntimeException(e);
        }
    }

    private ConnectorCreator() {
    }

    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, properties);
    }
}
