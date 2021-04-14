package com.airlines.dao.impl;

import com.airlines.dao.CrewMemberDao;
import com.airlines.exception.DaoOperationException;
import com.airlines.model.Airplane;
import com.airlines.model.CrewMember;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrewMemberDaoImpl implements CrewMemberDao {
    private static final String INSERT_QUERY = "INSERT INTO crew " +
            "(first_name, last_name, position, birthday, citizenship) VALUES (?, ?, ?, ?, ?) ;";

    private DataSource dataSource;

    public CrewMemberDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(CrewMember crew) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS);) {
            insertStatement.setString(1, crew.getFirstName());
            insertStatement.setString(2, crew.getLastName());
            insertStatement.setObject(3, crew.getPosition());
            insertStatement.setDate(4, Date.valueOf(crew.getBirthday()));
            insertStatement.setObject(5, crew.getCitizenship());
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
    public CrewMember findOne(Long id) {
        return null;
    }

    @Override
    public CrewMember link(Airplane airplane, Long id) {
        return null;
    }

    @Override
    public CrewMember update(CrewMember crewMember) {
        return null;
    }
}
