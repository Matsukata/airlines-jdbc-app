package com.util;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class JdbcUtil {
    static String DEFAULT_DATABASE_NAME = "airlines_app_db";
    static String DEFAULT_USERNAME = "airlines_app_user";
    static String DEFAULT_PASSWORD = "airlines_app_pass";

    public static DataSource createDefaultPostgresDataSource() {
        String url = formatPostgresDbUrl(DEFAULT_DATABASE_NAME);
        return createPostgresDataSource(url, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

    public static DataSource createPostgresDataSource(String url, String username, String pass) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(pass);
        return dataSource;
    }

    private static String formatPostgresDbUrl(String databaseName) {
        return String.format("jdbc:postgresql://localhost:5432/%s", databaseName);
    }

}
