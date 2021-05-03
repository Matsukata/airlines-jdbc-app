package com.airlines.util;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class TestDatasourceProvider {
    static String DATABASE_NAME = "jdbc:h2:mem:airlines_test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";
    static String USERNAME = "test";
    static String PASSWORD = "test";

    public static DataSource createTestDatasource() {
        JdbcDataSource h2DataSource = new JdbcDataSource();
        h2DataSource.setUser(USERNAME);
        h2DataSource.setPassword(PASSWORD);
        h2DataSource.setUrl(DATABASE_NAME);

        return h2DataSource;
    }

}
