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
    private static final String DELETE_QUERY = "DELETE FROM crews_crew_members WHERE crew_id = ? AND crew_member_id = ?;";

    private DataSource dataSource;

    public CrewDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(CrewMember crewMember, Crew crew) {
        if (crewMember == null || crewMember.getId() == null) {
            throw new IllegalArgumentException("Valid crewMember entity should be provided");
        }
        if (crew == null || crew.getId() == null) {
            throw new IllegalArgumentException("Valid crew entity should be provided");
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(ADD_CREW_MEMBER_BY_CREW_QUERY)) {
            insertStatement.setLong(1, crewMember.getId());
            insertStatement.setLong(2, crew.getId());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoOperationException(format("Cannot add crewMember with id = %d to crew with crewName = %s", crewMember.getId(), crew.getName()), e);
        }
    }

    @Override
    public List<CrewMember> getByCrewId(Long crewId) {
        if (crewId == null) {
            throw new IllegalArgumentException("Cannot find crew members for crew without id");
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CREW_ID_QUERY)) {
            preparedStatement.setLong(1, crewId);
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
            throw new DaoOperationException(format("Cannot find crew members for crew with id = %d", crewId), e);
        }
    }

    @Override
    public List<CrewMember> getByCrewName(String crewName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_CREW_NAME_QUERY)) {
            preparedStatement.setString(1, crewName);
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
            throw new DaoOperationException(format("Cannot find crew members for crew \'%s\'", crewName), e);
        }
    }

    @Override
    public void removeCrewMemberFromCrew(Crew crew, CrewMember crewMember) {
        if (crew == null || crew.getId() == null) {
            throw new IllegalArgumentException("Valid crew entity should be provided");
        }
        if (crewMember == null || crewMember.getId() == null) {
            throw new IllegalArgumentException("Valid crewMember entity should be provided");
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, crew.getId());
            statement.setLong(2, crewMember.getId());
            statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoOperationException(format("Cannot remove a crew member with id = %d from crew with id = %d", crewMember.getId(), crew.getId()), e);
        }
    }
}
