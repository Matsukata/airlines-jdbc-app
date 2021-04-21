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

public class AirplaneDaoImpl implements AirplaneDao {
    private static final String INSERT_QUERY = "INSERT INTO airplanes (codeName, model, manufactureDate, capacity, flightRange, crew) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String FIND_ALL_SQL = "SELECT * FROM airplanes;";
    private static final String SELECT_BY_CODE_NAME_SQL = "SELECT * FROM airplanes WHERE codeName = ?;";
    private static final String SELECT_BY_CREW_NAME_SQL = "SELECT * FROM crews WHERE crewName = ?;";
    private static final String DELETE_AIRPLANE_SQL = "delete from airplanes where id = ?;";
    private static final String UPDATE_AIRPLANE_SQL = "UPDATE airplanes SET codeName = ?, model = ?, manufactureDate = ?, capacity = ?, flightRange = ?, crew = ? WHERE id = ?;";

    private DataSource dataSource;

    public AirplaneDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Airplane airplane) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY,
                     PreparedStatement.RETURN_GENERATED_KEYS);) {
            insertStatement.setString(1, airplane.getCodeName());
            insertStatement.setString(2, airplane.getModel());
            insertStatement.setDate(3, Date.valueOf(airplane.getManufactureDate()));
            insertStatement.setInt(4, airplane.getCapacity());
            insertStatement.setInt(5, airplane.getFlightRange());
            insertStatement.setLong(6, (airplane.getCrew()).getId());
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(1);
                airplane.setId(id);
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Error saving product: " + airplane, e);
        }
    }

    @Override
    public List<Airplane> findAll() {
        List<Airplane> airplaneArray = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String codeName = rs.getString("codeName");
                String model = rs.getString("model");
                LocalDate manufactureDate = rs.getDate("manufactureDate").toLocalDate();
                int capacity = rs.getInt("capacity");
                int flightRange = rs.getInt("flightRange");
               // Crew crew = (rs.getLong("crew_id")).;
               // airplaneArray.add(new Airplane(id, codeName, model, manufactureDate, capacity, flightRange, crew));
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Couldn't save product ", e);
        }
        return airplaneArray;
    }

    @Override
    public Airplane findOne(String codeName) {
        Airplane airplane = new Airplane();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectByIdStatement = connection.prepareStatement(SELECT_BY_CODE_NAME_SQL);) {
            selectByIdStatement.setString(1, codeName);
            ResultSet resultSet = selectByIdStatement.executeQuery();
            if (resultSet.next()) {
                airplane.setId(resultSet.getLong("id"));
                airplane.setCodeName(resultSet.getString("name"));
                airplane.setModel(resultSet.getString("model"));
                airplane.setManufactureDate(resultSet.getDate("manufactureDate").toLocalDate());
                airplane.setCapacity(resultSet.getInt("capacity"));
                airplane.setFlightRange(resultSet.getInt("flightRange"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return airplane;
    }

    @Override
    public void remove(Airplane airplane) {
        if (airplane.getId() == null) {
            throw new DaoOperationException("Cannot find a product without ID");
        }
        if (airplane.getId() < 0) {
            throw new DaoOperationException(String.format("Product with id = %d does not exist", airplane.getId()));
        }
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_AIRPLANE_SQL);) {
            statement.setLong(1, airplane.getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Airplane findByCrewName(String crewName) {
        Airplane airplane = new Airplane();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectByCrewNameStatement = connection.prepareStatement(SELECT_BY_CREW_NAME_SQL);) {
            selectByCrewNameStatement.setString(1, crewName);
            ResultSet resultSet = selectByCrewNameStatement.executeQuery();
            if (resultSet.next()) {
                airplane.setId(resultSet.getLong("id"));
                airplane.setCodeName(resultSet.getString("name"));
                airplane.setModel(resultSet.getString("model"));
                airplane.setManufactureDate(resultSet.getDate("manufactureDate").toLocalDate());
                airplane.setCapacity(resultSet.getInt("capacity"));
                airplane.setFlightRange(resultSet.getInt("flightRange"));
                //airplane.setCrew(resultSet.getLong("crew"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return airplane;
    }

    @Override
    public void update(Airplane airplane, Crew crew) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_AIRPLANE_SQL, PreparedStatement.RETURN_GENERATED_KEYS);) {
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (airplane.getId() == null) {
                throw new DaoOperationException("Cannot find an airplane without ID");
            }
            if (airplane.getId() < 0) {
                throw new DaoOperationException(String.format("Airplane with id = %d does not exist", airplane.getId()));
            }
            if (generatedKeys.next()) {
                long id = generatedKeys.getLong(5);
                airplane.setId(id);
            }
            statement.setString(1, airplane.getCodeName());
            statement.setString(2, airplane.getModel());
            statement.setDate(3, Date.valueOf(airplane.getManufactureDate()));
            statement.setInt(4, (airplane.getCapacity()));
            statement.setInt(5, airplane.getFlightRange());
            statement.setLong(6, (airplane.getCrew()).getId());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}