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
import java.util.Optional;

import static java.lang.String.format;

public class CrewMemberDaoImpl implements CrewMemberDao {
    private static final String INSERT_QUERY = "INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_BY_ID_QUERY = "SELECT * FROM crew_members WHERE id = ?;";
    private static final String UPDATE_QUERY = "UPDATE crew_members SET first_name = ?, last_name = ?, position = ?, birthday = ?, citizenship = ? WHERE id = ?;";

    private DataSource dataSource;

    public CrewMemberDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<CrewMember> save(CrewMember crewMember) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS);) {
            insertStatement.setString(1, crewMember.getFirstName());
            insertStatement.setString(2, crewMember.getLastName());
            insertStatement.setString(3, crewMember.getPosition().name());
            insertStatement.setDate(4, Date.valueOf(crewMember.getBirthday()));
            insertStatement.setString(5, crewMember.getCitizenship().name());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                return Optional.of(copyAttributesAndSetId(crewMember, id));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Cannot save crewMember: " + crewMember, e);
        }
    }

    @Override
    public Optional<CrewMember> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot find the member because id is not provided");
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectByIdStatement = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectByIdStatement.setLong(1, id);
            ResultSet resultSet = selectByIdStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(CrewMember.builder()
                        .withId(resultSet.getLong("id"))
                        .withFirstName(resultSet.getString("first_name"))
                        .withLastName(resultSet.getString("last_name"))
                        .withPosition(Position.valueOf(resultSet.getString("position")))
                        .withBirthday(resultSet.getDate("birthday").toLocalDate())
                        .withCitizenship(Citizenship.valueOf(resultSet.getString("citizenship")))
                        .build());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoOperationException(format("Crew member with id = %d was not found", id), e);
        }
    }

    @Override
    public void update(CrewMember crewMember) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            if (crewMember.getId() == null) {
                throw new IllegalArgumentException("CrewMember id should not be null");
            }
            statement.setString(1, crewMember.getFirstName());
            statement.setString(2, crewMember.getLastName());
            statement.setString(3, crewMember.getPosition().toString());
            statement.setDate(4, Date.valueOf(crewMember.getBirthday()));
            statement.setString(5, crewMember.getCitizenship().toString());
            statement.setLong(6, crewMember.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoOperationException(format("Cannot update a crew member with id = %d", crewMember.getId()), e);
        }
    }

    private CrewMember copyAttributesAndSetId(CrewMember crewMember, Long id) {
        return CrewMember.builder()
                .withId(id)
                .withFirstName(crewMember.getFirstName())
                .withLastName(crewMember.getLastName())
                .withPosition(crewMember.getPosition())
                .withBirthday(crewMember.getBirthday())
                .withCitizenship(crewMember.getCitizenship())
                .build();
    }
}
