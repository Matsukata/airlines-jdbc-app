package com.airlines.dao.impl;

import com.airlines.dao.AirplaneDao;
import com.airlines.exception.DaoOperationException;
import com.airlines.model.Airplane;
import com.airlines.model.Crew;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class AirplaneDaoImpl implements AirplaneDao {
    private static final String INSERT_QUERY = "INSERT INTO airplanes (codeName, model, manufactureDate, capacity, flightRange, crew) VALUES (?, ?, ?, ?, ?, ?) ;";
    private static final String FIND_ALL_QUERY = "SELECT * FROM airplanes;";
    private static final String SELECT_BY_CODE_NAME_QUERY = "SELECT * FROM airplanes WHERE codeName = ?;";
    private static final String DELETE_AIRPLANE_QUERY = "DELETE FROM airplanes WHERE id = ?;";
    private static final String SELECT_BY_CREW_NAME_QUERY = "SELECT * FROM airplanes LEFT JOIN crews ON airplanes.crew_id = crews.id WHERE crews.name = ?;";
    private static final String UPDATE_WITH_CREW_QUERY = "UPDATE airplanes SET crew_id = crews.id FROM crews WHERE crews.id = ? and airplanes.id = ?;";

    private DataSource dataSource;

    public AirplaneDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Airplane> save(Airplane airplane) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS);) {
            insertStatement.setString(1, airplane.getCodeName());
            insertStatement.setString(2, airplane.getModel());
            insertStatement.setDate(3, Date.valueOf(airplane.getManufactureDate()));
            insertStatement.setInt(4, airplane.getCapacity());
            insertStatement.setInt(5, airplane.getFlightRange());
            insertStatement.setObject(6, airplane.getCrew());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                return Optional.of(copyAttributesAndSetAirplaneId(airplane, id));
            } else {
                return Optional.empty();
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
                Crew crew = (Crew) rs.getObject("crew");
                airplaneArray.add(Airplane.builder()
                        .withId(id)
                        .withCodeName(codeName)
                        .withModel(model)
                        .withManufactureDate(manufactureDate)
                        .withCapacity(capacity)
                        .withFlightRange(flightRange)
                        .withCrew(crew)
                        .build());
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Couldn't find an airplane ", e);
        }
        return airplaneArray;
    }

    @Override
    public Optional<Airplane> findByCodeName(String codeName) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectByCodeNameStatement = connection.prepareStatement(SELECT_BY_CODE_NAME_QUERY);) {
            selectByCodeNameStatement.setString(1, codeName);
            ResultSet resultSet = selectByCodeNameStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Airplane.builder()
                        .withId(resultSet.getLong("id"))
                        .withCodeName(resultSet.getString("name"))
                        .withModel(resultSet.getString("model"))
                        .withManufactureDate(resultSet.getDate("manufactureDate").toLocalDate())
                        .withCapacity(resultSet.getInt("capacity"))
                        .withFlightRange(resultSet.getInt("flightRange"))
                        .withCrew((Crew) resultSet.getObject("crew"))
                        .build());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Couldn't find an airplane ", e);
        }
    }

    @Override
    public void remove(Long id) {
        if (id == null) {
            throw new DaoOperationException("Cannot remove an airplane without id");
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_AIRPLANE_QUERY);) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoOperationException("Cannot remove an airplane", e);
        }
    }

    @Override
    public Optional<Airplane> findByCrewName(String crewName) {
        if (crewName == null) {
            throw new DaoOperationException("Cannot find an airplane because the name is not provided");
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectByCrewNameStatement = connection.prepareStatement(SELECT_BY_CREW_NAME_QUERY)) {
            selectByCrewNameStatement.setString(1, crewName);
            ResultSet resultSet = selectByCrewNameStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Airplane.builder()
                        .withId(resultSet.getLong("id"))
                        .withCodeName(resultSet.getString("name"))
                        .withModel(resultSet.getString("model"))
                        .withManufactureDate(resultSet.getDate("manufactureDate").toLocalDate())
                        .withCapacity(resultSet.getInt("capacity"))
                        .withFlightRange(resultSet.getInt("flightRange"))
                        .withCrew((Crew) resultSet.getObject("crew"))
                        .build());
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoOperationException(format("An airplane with crew name = %s was not found", crewName), e);
        }
    }

    @Override
    public void update(Airplane airplane, Crew crew) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_WITH_CREW_QUERY)) {
            if (airplane.getId() == null) {
                throw new IllegalArgumentException("Airplane id should not be null");
            }
            statement.setString(1, airplane.getCodeName());
            statement.setString(2, airplane.getModel());
            statement.setDate(3, Date.valueOf(airplane.getManufactureDate()));
            statement.setInt(4, airplane.getCapacity());
            statement.setInt(5, airplane.getCapacity());
            statement.setObject(6, airplane.getCrew());
            statement.setLong(7, airplane.getId());
            statement.setLong(8, crew.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoOperationException(format("Can not update an airplane with id = %d", airplane.getId()), e);
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
                .withCrew(airplane.getCrew())
                .build();
    }
}