package net.zypr.UotakeCore.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBSource {
    private final HikariDataSource ds;
    private static final String DRIVER_CLASS = "org.mariadb.jdbc.Driver";
    private static final String JDBC_URL = "";
    private static final String USERNAME = "uotake";
    private static final String PASSWORD = "";

    public DBSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(DRIVER_CLASS);
        config.setJdbcUrl(JDBC_URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
