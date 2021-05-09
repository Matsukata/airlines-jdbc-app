package com.airlines.dao.impl;

import com.airlines.dao.AirplaneDao;
import com.airlines.exception.DaoOperationException;
import com.airlines.model.Airplane;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class AirplaneDaoImpl implements AirplaneDao {
    private static final String INSERT_QUERY = "INSERT INTO airplanes (code_name, model, manufacture_date, capacity, flight_range, crew_id) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String FIND_ALL_QUERY = "SELECT * FROM airplanes;";
    private static final String SELECT_BY_CODE_NAME_QUERY = "SELECT * FROM airplanes WHERE code_name = ?;";
    private static final String DELETE_QUERY = "DELETE FROM airplanes WHERE id = ?;";
    private static final String SELECT_BY_CREW_NAME_QUERY = "SELECT * FROM airplanes LEFT JOIN crews ON airplanes.crew_id = crews.id WHERE crews.name = ?;";
    private static final String UPDATE_WITH_CREW_QUERY = "UPDATE airplanes SET crew_id = ? WHERE airplanes.id = ?;";

    private DataSource dataSource;

    public AirplaneDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Airplane> save(Airplane airplane) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, airplane.getCodeName());
            insertStatement.setString(2, airplane.getModel());
            insertStatement.setDate(3, Date.valueOf(airplane.getManufactureDate()));
            insertStatement.setInt(4, airplane.getCapacity());
            insertStatement.setInt(5, airplane.getFlightRange());
            insertStatement.setLong(6, airplane.getCrewId());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                return Optional.of(copyAttributesAndSetAirplaneId(airplane, id));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Cannot save an airplane: " + airplane, e);
        }
    }

    @Override
    public List<Airplane> findAll() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Airplane> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(Airplane.builder()
                        .withId(resultSet.getLong("id"))
                        .withCodeName(resultSet.getString("code_name"))
                        .withModel(resultSet.getString("model"))
                        .withManufactureDate(resultSet.getDate("manufacture_date").toLocalDate())
                        .withCapacity(resultSet.getInt("capacity"))
                        .withFlightRange(resultSet.getInt("flight_range"))
                        .withCrewId(resultSet.getLong("crew_id"))
                        .build());
            }
            return Collections.unmodifiableList(list);
        } catch (SQLException e) {
            throw new DaoOperationException("Cannot find airplanes", e);
        }
    }

    @Override
    public Optional<Airplane> findByCodeName(String codeName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectByCodeNameStatement = connection.prepareStatement(SELECT_BY_CODE_NAME_QUERY)) {
            selectByCodeNameStatement.setString(1, codeName);
            ResultSet resultSet = selectByCodeNameStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Airplane.builder()
                        .withId(resultSet.getLong("id"))
                        .withCodeName(resultSet.getString("code_name"))
                        .withModel(resultSet.getString("model"))
                        .withManufactureDate(resultSet.getDate("manufacture_date").toLocalDate())
                        .withCapacity(resultSet.getInt("capacity"))
                        .withFlightRange(resultSet.getInt("flight_range"))
                        .withCrewId(resultSet.getLong("crew_id"))
                        .build());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoOperationException(format("Cannot find an airplane with code name = %s", codeName), e);
        }
    }

    @Override
    public void removeById(Long airplaneId) {
        if (airplaneId == null) {
            throw new IllegalArgumentException("Cannot remove an airplane without id");
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, airplaneId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoOperationException(format("Cannot remove an airplane with id = %s", airplaneId), e);
        }
    }

    @Override
    public Optional<Airplane> findByCrewName(String crewName) {
        if (crewName == null) {
            throw new IllegalArgumentException("Cannot find an airplane because the name is not provided");
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectByCrewNameStatement = connection.prepareStatement(SELECT_BY_CREW_NAME_QUERY)) {
            selectByCrewNameStatement.setString(1, crewName);
            ResultSet resultSet = selectByCrewNameStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Airplane.builder()
                        .withId(resultSet.getLong("id"))
                        .withCodeName(resultSet.getString("code_name"))
                        .withModel(resultSet.getString("model"))
                        .withManufactureDate(resultSet.getDate("manufacture_date").toLocalDate())
                        .withCapacity(resultSet.getInt("capacity"))
                        .withFlightRange(resultSet.getInt("flight_range"))
                        .withCrewId(resultSet.getLong("crew_id"))
                        .build());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoOperationException(format("Cannot find an airplane with crew name = %s", crewName), e);
        }
    }

    @Override
    public void updateWithCrewId(Airplane airplane, Long crewId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_WITH_CREW_QUERY)) {
            if (crewId == null) {
                throw new IllegalArgumentException("Airplane crewId should not be null");
            }
            statement.setLong(1, crewId);
            statement.setLong(2, airplane.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoOperationException(format("Cannot update an airplane with crewId = %d", crewId), e);
        }
    }

    private Airplane copyAttributesAndSetAirplaneId(Airplane airplane, Long id) {
        return Airplane.builder()
                .withId(id)
                .withCodeName(airplane.getCodeName())
                .withModel(airplane.getModel())
                .withManufactureDate(airplane.getManufactureDate())
                .withCapacity(airplane.getCapacity())
                .withFlightRange(airplane.getFlightRange())
                .withCrewId(airplane.getCrewId())
                .build();
    }
}