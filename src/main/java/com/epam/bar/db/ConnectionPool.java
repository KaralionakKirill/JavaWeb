package com.epam.bar.db;

import com.epam.bar.exception.ConnectionException;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public enum ConnectionPool {
    INSTANCE;

    private final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private final int DEFAULT_POOL_SIZE = 32;
    private final BlockingQueue<ProxyConnection> freeConnection;
    private final Queue<ProxyConnection> givenAwayConnection;

    ConnectionPool() {
        freeConnection = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenAwayConnection = new ArrayDeque<>();
        initPool();
        if(freeConnection.size() != DEFAULT_POOL_SIZE){
            int amount = DEFAULT_POOL_SIZE - freeConnection.size();
            for (int i = 0; i < amount; i++) {
                try {
                    ProxyConnection connection = new ProxyConnection(ConnectorCreator.getConnection());
                    freeConnection.offer(connection);
                } catch (SQLException e) {
                    logger.error(e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void initPool(){
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                ProxyConnection connection = new ProxyConnection(ConnectorCreator.getConnection());
                freeConnection.offer(connection);
            } catch (SQLException e) {
                logger.error(e);
                throw new RuntimeException(e);
            }
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnection.take();
            givenAwayConnection.offer(connection);
        } catch (InterruptedException e) {
            logger.error("Connection interrupted");
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws ConnectionException {
        if (connection instanceof ProxyConnection) {
            givenAwayConnection.remove(connection);
            freeConnection.offer((ProxyConnection) connection);
        } else {
            throw new ConnectionException("Connection mismatch");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnection.take().finalClose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error(e);
            }
        });
    }
}
