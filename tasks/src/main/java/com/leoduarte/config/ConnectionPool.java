package com.leoduarte.config;

import com.leoduarte.servlet.TaskServlet;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionPool {

    private static ConnectionPool instance;
    private HikariDataSource dataSource;

    private static final Logger log = LoggerFactory.getLogger(TaskServlet.class);

    private ConnectionPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(System.getenv("DB_URL"));
        config.setUsername(System.getenv("DB_USER"));
        config.setPassword(System.getenv("DB_PASS"));
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.setMaximumPoolSize(Integer.parseInt(System.getenv("DB_POOL_SIZE")));
        config.setMinimumIdle(Integer.parseInt(System.getenv("DB_MINIMUM_IDLE")));
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

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error("Error trying to get database connection", e);
            throw new RuntimeException("Error trying to get database connection", e);
        }
    }

    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
