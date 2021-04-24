package com.airlines.dao.impl;

import com.airlines.dao.AirplaneDao;
import com.airlines.exception.DaoOperationException;
import com.airlines.model.Airplane;
import com.airlines.model.Crew;
import com.airlines.model.CrewMember;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AirplaneDaoImpl implements AirplaneDao {
    private static final String INSERT_QUERY = "INSERT INTO airplanes " +
            "(codeName, model, manufactureDate, capacity, flightRange, crew) VALUES (?, ?, ?, ?, ?, ?) ;";
    private static final String FIND_ALL_QUERY = "SELECT * FROM airplanes;";
    private static final String SELECT_BY_CODE_NAME_QUERY = "SELECT * FROM airplanes WHERE codeName = ?;";
    private static final String DELETE_QUERY = "delete from airplanes where id = ?;";

    private DataSource dataSource;

    public AirplaneDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Airplane airplane) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, airplane.getCodeName());
            insertStatement.setString(2, airplane.getModel());
            insertStatement.setDate(3, Date.valueOf(airplane.getManufactureDate()));
            insertStatement.setInt(4, airplane.getCapacity());
            insertStatement.setInt(5, airplane.getFlightRange());
            insertStatement.setObject(6, airplane.getCrewMember());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                Airplane.builder().withId(id);
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Error saving airplane: " + airplane, e);
        }
    }

    @Override
    public List<Airplane> findAll() {
        List<Airplane> airplaneArray = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String codeName = rs.getString("codeName");
                String model = rs.getString("model");
                LocalDate manufactureDate = rs.getDate("manufactureDate").toLocalDate();
                int capacity = rs.getInt("capacity");
                int flightRange = rs.getInt("flightRange");
                CrewMember crewMember = (CrewMember) rs.getObject("Crew");
                airplaneArray.add(Airplane.builder()
                        .withId(id)
                        .withCodeName(codeName)
                        .withModel(model)
                        .withManufactureDate(manufactureDate)
                        .withCapacity(capacity)
                        .withFlightRange(flightRange)
                        .withCrewMember(crewMember).build());
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Couldn't find airplane", e);
        }
        return airplaneArray;
    }

    @Override
    public Airplane findOne(String codeName) {
        Airplane airplane = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectByCodeNameStatement = connection.prepareStatement(SELECT_BY_CODE_NAME_QUERY);) {
            selectByCodeNameStatement.setString(1, codeName);
            ResultSet resultSet = selectByCodeNameStatement.executeQuery();
            if (resultSet.next()) {
                airplane = Airplane.builder()
                        .withId(resultSet.getLong("id"))
                        .withCodeName(resultSet.getString("name"))
                        .withModel(resultSet.getString("model"))
                        .withManufactureDate(resultSet.getDate("manufactureDate").toLocalDate())
                        .withCapacity(resultSet.getInt("capacity"))
                        .withFlightRange(resultSet.getInt("flightRange"))
                        .build();
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Could not find airplane", e);
        }
        return airplane;
    }

    @Override
    public void remove(Airplane airplane) {
        if (airplane.getId() == null) {
            throw new DaoOperationException("Cannot remove an airplane without id");
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, airplane.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoOperationException("Could not remove airplane", e);
        }
    }

    @Override
    public Airplane findByCrewName(String crewName) {
        return null;
    }

    @Override
    public Airplane update(Crew crew) {
        return null;
    }
}