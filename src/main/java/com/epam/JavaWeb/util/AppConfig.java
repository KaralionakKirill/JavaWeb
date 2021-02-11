package com.epam.JavaWeb.util;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Properties;

@Log4j2
@Getter
public class AppConfig {
    private static volatile AppConfig instance;
    private final Properties properties;
    public static final String APP_PROPERTIES= "application.properties";
    public static final String SERVER_PORT= "server.port";
    private static final String SERVER_HOST = "server.host";
    private static final String APP_POINTS = "app.pointsPerDollar";
    private static final String APP_MINUS = "app.minusToBlock";

    public static AppConfig getInstance() {
        AppConfig localInstance = instance;
        if (localInstance == null) {
            synchronized (AppConfig.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new AppConfig();
                }
            }
        }
        return localInstance;
    }

    private Integer pointsPerDollar;
    private Integer minusToBlock;
    private String serverHost;
    private String serverPort;

    private AppConfig() {
        properties = new Properties();
        try {
            ClassLoader classLoader = AppConfig.class.getClassLoader();
            properties.load(classLoader.getResourceAsStream(APP_PROPERTIES));
            init();
        } catch (IOException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    private void init() {
        pointsPerDollar = Integer.parseInt(properties.getProperty("app.pointsPerDollar"));
        minusToBlock = Integer.parseInt(properties.getProperty("app.minusToBlock"));
        serverHost = properties.getProperty("server.host");
        serverPort = properties.getProperty("server.port");
    }

}
