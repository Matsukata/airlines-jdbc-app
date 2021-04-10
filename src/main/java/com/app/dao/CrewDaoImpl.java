package com.app.dao;

import com.app.exception.DaoOperationException;
import com.app.model.Airplane;
import com.app.model.Crew;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrewDaoImpl implements CrewDao {

    private DataSource dataSource;

    public CrewDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Crew crew) {
        String query = "INSERT INTO crew " +
                "(first_name, last_name, position, birthday, citizenship) VALUES (?, ?, ?, ?, ?) ;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(query,
                     PreparedStatement.RETURN_GENERATED_KEYS);) {
            insertStatement.setString(1, crew.getFirstName());
            insertStatement.setString(2, crew.getLastName());
            insertStatement.setString(3, crew.getPosition());
            insertStatement.setDate(4, Date.valueOf(crew.getBirthday()));
            insertStatement.setString(5, crew.getCitizenship());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                crew.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Error saving crew: " + crew, e);
        }
    }

    @Override
    public Crew findOne(Long id) {
        return null;
    }

    @Override
    public Crew link(Airplane airplane, Long id) {
        return null;
    }
}
