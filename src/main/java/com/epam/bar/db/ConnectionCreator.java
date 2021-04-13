package com.epam.bar.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.Properties;

/**
 * The class create connection with database
 *
 * @author Kirill Karalionak
 * @version 1.0.0
 */
final class ConnectionCreator {
    private static final Logger logger = LogManager.getLogger(ConnectionCreator.class);
    private static final String DB_PROPERTIES_PATH = "property/database.properties";
    private static final String DB_URL = "db.url";
    private static final String DB_DRIVER = "db.driver";
    private static final Properties properties = new Properties();
    private static final String DATABASE_URL;

    static {
        try {
            ClassLoader classLoader = ConnectionCreator.class.getClassLoader();
            properties.load(classLoader.getResourceAsStream(DB_PROPERTIES_PATH));
            String driverName = (String) properties.get(DB_DRIVER);
            Class.forName(driverName);
            DATABASE_URL = (String) properties.get(DB_URL);
        } catch (IOException | ClassNotFoundException | MissingResourceException e) {
            logger.fatal("fatal error: config file" + e);
            throw new RuntimeException(e);
        }
    }

    private ConnectionCreator() {
    }

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    protected static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, properties);
    }
}
