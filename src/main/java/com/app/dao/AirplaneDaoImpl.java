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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AirplaneDaoImpl implements AirplaneDao {
    private DataSource dataSource;

    public AirplaneDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Airplane airplane) {
        String query = "INSERT INTO airplane " +
                "(codeName, model, manufactureDate, capacity, flightRange) VALUES (?, ?, ?, ?, ?) ;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(query,
                     PreparedStatement.RETURN_GENERATED_KEYS);) {
            insertStatement.setString(1, airplane.getCodeName());
            insertStatement.setString(2, airplane.getModel());
            insertStatement.setDate(3, Date.valueOf(airplane.getManufactureDate()));
            insertStatement.setInt(4, airplane.getCapacity());
            insertStatement.setInt(5, airplane.getFlightRange());
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
        String FIND_ALL_SQL = "SELECT * FROM airplane;";
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

                airplaneArray.add(new Airplane(id, codeName, model, manufactureDate, capacity, flightRange));
            }
        } catch (SQLException e) {
            throw new DaoOperationException("Couldn't save product ", e);
        }
        return airplaneArray;
    }

    @Override
    public Airplane findOne(String codeName) {
        String SELECT_BY_CODE_NAME_SQL = "SELECT * FROM airplane WHERE codeName = ?;";
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
        String DELETE_AIRPLANE_SQL = "delete from airplane where id = ?;";
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
    public Airplane findOne(Crew firstName, Crew lastName) {
        return null;
    }
}
