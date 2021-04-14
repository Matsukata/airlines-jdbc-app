package com.airlines.dao;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class DbProvider {
    private static ResourceBundle rb = ResourceBundle.getBundle("airlines");

    public static DataSource createDefaultPostgresDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(rb.getString("airlines.url"));
        dataSource.setUser(rb.getString("airlines.user"));
        dataSource.setPassword(rb.getString("airlines.password"));
        return dataSource;
    }
}
