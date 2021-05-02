package com.airlines.dao.impl;

import com.airlines.dao.CrewDao;
import com.airlines.exception.DaoOperationException;
import com.airlines.model.Citizenship;
import com.airlines.model.Crew;
import com.airlines.model.CrewMember;
import com.airlines.model.Position;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

public class CrewDaoImpl implements CrewDao {
    private static final String ADD_CREW_MEMBER_BY_CREW_QUERY = "INSERT INTO crews_crew_members (crew_id, crew_member_id) VALUES (?, ?);";
    private static final String SELECT_BY_CREW_ID_QUERY = "SELECT * FROM crew_members LEFT JOIN crews_crew_members ON crew_members.id = crews_crew_members.crew_member_id WHERE crews_crew_members.crew_id = ?;";
    private static final String SELECT_BY_CREW_NAME_QUERY = "SELECT * FROM crew_members INNER JOIN crews_crew_members ON crew_members.id = crews_crew_members.crew_member_id INNER JOIN crews ON crews_crew_members.crew_id = crews.id WHERE crews.name = ?;";
    private static final String DELETE_QUERY = "DELETE FROM crew_members WHERE id = ?;";

    private DataSource dataSource;

    public CrewDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(CrewMember crewMember, Crew crew) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(ADD_CREW_MEMBER_BY_CREW_QUERY)) {
            insertStatement.setLong(1, crewMember.getId());
            insertStatement.setLong(2, crew.getId());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoOperationException("Cannot add crewMember: " + crewMember, e);
        }
    }

    @Override
    public List<CrewMember> getByCrewId(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CREW_ID_QUERY)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CrewMember> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(CrewMember.builder()
                        .withId(resultSet.getLong("id"))
                        .withFirstName(resultSet.getString("first_name"))
                        .withLastName(resultSet.getString("last_name"))
                        .withPosition(Position.valueOf(resultSet.getString("position")))
                        .withBirthday(resultSet.getDate("birthday").toLocalDate())
                        .withCitizenship(Citizenship.valueOf(resultSet.getString("citizenship")))
                        .build());
            }
            return Collections.unmodifiableList(list);
        } catch (SQLException e) {
            throw new DaoOperationException(format("Cannot find crew members with crew id = %d", id), e);
        }
    }

    @Override
    public List<CrewMember> getByCrewName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CREW_NAME_QUERY)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CrewMember> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(CrewMember.builder()
                        .withId(resultSet.getLong("id"))
                        .withFirstName(resultSet.getString("first_name"))
                        .withLastName(resultSet.getString("last_name"))
                        .withPosition(Position.valueOf(resultSet.getString("position")))
                        .withBirthday(resultSet.getDate("birthday").toLocalDate())
                        .withCitizenship(Citizenship.valueOf(resultSet.getString("citizenship")))
                        .build());
            }
            return Collections.unmodifiableList(list);
        } catch (SQLException e) {
            throw new DaoOperationException(format("Cannot find crew members with crew name = %s", name), e);
        }
    }

    @Override
    public void removeCrewMemberFromCrew(CrewMember crewMember) {
        if (crewMember == null) {
            throw new IllegalArgumentException("Cannot remove a crew member because it is not provided");
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, crewMember.getId());
            statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoOperationException(format("Cannot remove a crew member with id = %d", crewMember.getId()), e);
        }
    }
}
