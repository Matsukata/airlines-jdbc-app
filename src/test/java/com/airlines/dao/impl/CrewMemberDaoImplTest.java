package com.airlines.dao.impl;

import com.airlines.dao.CrewMemberDao;
import com.airlines.model.Citizenship;
import com.airlines.model.CrewMember;
import com.airlines.model.Position;
import com.airlines.util.JdbcUtil;
import com.airlines.util.TestDatasourceProvider;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class CrewMemberDaoImplTest {
    private static final String CREATE_SCHEMA = JdbcUtil.getSqlQueryString("C:\\Users\\Aleksandra\\IdeaProjects\\airlines-jdbc-app\\src\\main\\resources\\db\\schema.sql");
    private static final String CREATE_TEST_DATA = JdbcUtil.getSqlQueryString("C:\\Users\\Aleksandra\\IdeaProjects\\airlines-jdbc-app\\src\\main\\resources\\db\\test_data.sql");
    DataSource dataSource = TestDatasourceProvider.createTestDatasource();
    CrewMemberDao crewMemberDao = new CrewMemberDaoImpl(dataSource);

    @Test
    public void save() {
        try (Connection connection = dataSource.getConnection()) {
            Statement createTableStatement = connection.createStatement();
            createTableStatement.executeUpdate(CREATE_SCHEMA);
            createTableStatement.executeUpdate(CREATE_TEST_DATA);

            CrewMember crewMember = generateCrewMember();
            CrewMember newCrewMember = crewMemberDao.save(crewMember).get();
            CrewMember crewMemberById = crewMemberDao.findById(11L).get();

            assertEquals(newCrewMember, crewMemberById);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void findById() {
        try (Connection connection = dataSource.getConnection()) {
            Statement createTableStatement = connection.createStatement();
            createTableStatement.executeUpdate(CREATE_SCHEMA);
            createTableStatement.executeUpdate(CREATE_TEST_DATA);

            CrewMember crewMember = generateCrewMember();
            CrewMember newCrewMember = crewMemberDao.save(crewMember).get();
            CrewMember crewMemberById = crewMemberDao.findById(11L).get();

            assertEquals(newCrewMember.getId(), crewMemberById.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void update() {
        try (Connection connection = dataSource.getConnection()) {
            Statement createTableStatement = connection.createStatement();
            createTableStatement.executeUpdate(CREATE_SCHEMA);
            createTableStatement.executeUpdate(CREATE_TEST_DATA);

            CrewMember testCrewMember = generateCrewMember();
            CrewMember testSaveCrewMember = crewMemberDao.save(testCrewMember).get();

            CrewMember newCrewMember = crewMemberDao.save(testCrewMember).get();
            crewMemberDao.update(newCrewMember);

            assertEquals(testSaveCrewMember, newCrewMember);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private CrewMember generateCrewMember() {
        return CrewMember.builder()
                .withFirstName("Mary")
                .withLastName("Johnes")
                .withPosition(Position.FLIGHT_ENGINEER)
                .withBirthday(LocalDate.of(2000, 4, 23))
                .withCitizenship(Citizenship.ITALIAN)
                .build();
    }
}