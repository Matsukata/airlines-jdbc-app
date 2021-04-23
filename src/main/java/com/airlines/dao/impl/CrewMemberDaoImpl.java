package com.airlines.dao.impl;

import com.airlines.dao.CrewMemberDao;
import com.airlines.exception.DaoOperationException;
import com.airlines.model.Citizenship;
import com.airlines.model.CrewMember;
import com.airlines.model.Position;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrewMemberDaoImpl implements CrewMemberDao {
    private static final String INSERT_QUERY = "INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM crew_members WHERE id = ?;";
    private static final String UPDATE_CREW_MEMBERS_SQL = "UPDATE crew_members SET first_name = ?, last_name = ?, position = ?, birthday = ?, citizenship = ? WHERE id = ?;";

    private DataSource dataSource;

    public CrewMemberDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(CrewMember crewMember) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS);) {
            insertStatement.setString(1, crewMember.getFirstName());
            insertStatement.setString(2, crewMember.getLastName());
            insertStatement.setObject(3, crewMember.getPosition());
            insertStatement.setDate(4, Date.valueOf(crewMember.getBirthday()));
            insertStatement.setObject(5, crewMember.getCitizenship());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                crewMember.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Error saving a member of the crew: " + crewMember, e);
        }
    }

    @Override
    public CrewMember findById(Long id) {
        if (id == null) {
            throw new DaoOperationException("Cannot find the member because Id is not provided");
        }
        CrewMember crewMember = new CrewMember();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectByIdStatement = connection.prepareStatement(SELECT_BY_ID_SQL);) {
            selectByIdStatement.setLong(1, id);
            ResultSet resultSet = selectByIdStatement.executeQuery();
            if (resultSet.next()) {
                crewMember.setId(resultSet.getLong("id"));
                crewMember.setFirstName(resultSet.getString("first_name"));
                crewMember.setLastName(resultSet.getString("last_name"));
                crewMember.setPosition(Position.valueOf(resultSet.getString("position")));
                crewMember.setCitizenship(Citizenship.valueOf(resultSet.getString("citizenship")));
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Failed to find a member with Id = %d" + id, e);
        }
        return crewMember;
    }

    @Override
    public CrewMember update(CrewMember crewMember) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CREW_MEMBERS_SQL)) {
            if (crewMember.getId() == null) {
                throw new IllegalArgumentException("Cannot update the member because Id is not provided");
            }
            statement.setString(1, crewMember.getFirstName());
            statement.setString(2, crewMember.getLastName());
            statement.setString(3, crewMember.getPosition().toString());
            statement.setDate(4, Date.valueOf(crewMember.getBirthday()));
            statement.setString(5, crewMember.getCitizenship().toString());
            statement.setLong(6, crewMember.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoOperationException("Failed to update a member with Id = %d" + crewMember.getId(), e);
        }
        return crewMember;
    }
}
