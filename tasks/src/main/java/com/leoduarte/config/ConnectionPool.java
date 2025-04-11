package com.leoduarte.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static ConnectionPool instance;
    private HikariDataSource dataSource;

    private ConnectionPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(System.getenv("DB_URL"));
        config.setUsername(System.getenv("DB_USER"));
        config.setPassword(System.getenv("DB_PASSWORD"));

        config.setMaximumPoolSize(Integer.parseInt(System.getenv("DB_POOL_SIZE")));
        config.setMinimumIdle(Integer.parseInt(System.getenv("DB_MINUMUM_IDLE")));
        config.setIdleTimeout(Integer.parseInt(System.getenv("DB_IDLE_TIMEOUT")));
        config.setConnectionTimeout(Integer.parseInt(System.getenv("DB_CONNECTION_TIMEOUT")));

        dataSource = new HikariDataSource(config);
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }

        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
