package com.airlines.dao.impl;

import com.airlines.dao.AirplaneDao;
import com.airlines.model.Airplane;
import com.airlines.model.Crew;
import com.airlines.model.CrewMember;
import com.airlines.util.JdbcUtil;
import com.airlines.util.TestDatasourceProvider;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.airlines.model.Citizenship.ITALIAN;
import static com.airlines.model.Position.PILOT_IN_COMMAND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class AirplaneDaoImplTest {
    private static final String SCHEMA_SQL_PATH = "src/test/resources/sql/schema.sql";
    private static final String DATA_SQL_PATH = "src/test/resources/sql/test_data.sql";
    private static final String CREATE_TABLES_QUERY = JdbcUtil.getSqlQueryString(SCHEMA_SQL_PATH);
    private static final String GENERATE_DATA_QUERY = JdbcUtil.getSqlQueryString(DATA_SQL_PATH);

    private static final Airplane AIRPLANE = buildTestAirplane();
    private static final Airplane TEST_AIRPLANE = buildAirplane();
    private static final Airplane INVALID_AIRPLANE = buildInvalidAirplane();
    private static final Crew CREW = buildCrewFromDataSet();
    private static final Crew INVALID_CREW = buildInvalidCrewFromDataSet();

    private static AirplaneDao airplaneDao;
    private static DataSource dataSource;

    @BeforeClass
    public static void init() {
        dataSource = TestDatasourceProvider.createTestDatasource();
        airplaneDao = new AirplaneDaoImpl(dataSource);
    }

    @Before
    public void dataBaseInit() throws SQLException {
        createTables();
    }

    @Test
    public void shouldSaveAirplane() throws SQLException {
        populateTestData();

        Optional<Airplane> savedAirplane = airplaneDao.save(AIRPLANE);

        assertTrue(savedAirplane.isPresent());
        assertEquals(AIRPLANE, savedAirplane.get());
    }

    @Test
    public void shouldFindAll() throws SQLException {
        populateTestData();

        List<Airplane> list = airplaneDao.findAll();

        assertEquals(5, list.size());

        Airplane airplane1 = list.get(0);
        Airplane airplane2 = list.get(1);
        Airplane airplane3 = list.get(2);

        assertEquals("THD-342", airplane1.getCodeName());
        assertEquals("А319", airplane1.getModel());

        assertEquals("SGR-567", airplane2.getCodeName());
        assertEquals("А320", airplane2.getModel());

        assertEquals("KLG-467", airplane3.getCodeName());
        assertEquals("737", airplane3.getModel());
    }

    @Test
    public void shouldFindByCodeName() throws SQLException {
        populateTestData();

        Optional<Airplane> airplane = airplaneDao.findByCodeName("THD-342");

        assertEquals(TEST_AIRPLANE, airplane.get());
    }

    @Test
    public void shouldRemoveById() throws SQLException {
        populateTestData();
        airplaneDao.save(AIRPLANE);
        List<Airplane> listBeforeRemove = airplaneDao.findAll();

        airplaneDao.removeById(AIRPLANE.getId());

        assertEquals(6, listBeforeRemove.size());
        assertTrue(listBeforeRemove.contains(AIRPLANE));

        List<Airplane> listAfterRemove = airplaneDao.findAll();
        assertFalse(listAfterRemove.contains(AIRPLANE));
        assertEquals(5, listAfterRemove.size());
    }

    @Test
    public void shouldThrowExceptionIfAirplaneWithNoIDProvidedToRemoveMethod() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> airplaneDao.removeById(INVALID_AIRPLANE.getId()));

        assertEquals("Cannot remove an airplane without id", exception.getMessage());
    }

    @Test
    public void shouldFindByCrewName() throws SQLException {
        populateTestData();

        Optional<Airplane> airplane = airplaneDao.findByCrewName("Vityaz");

        assertEquals(TEST_AIRPLANE, airplane.get());
    }

    @Test
    public void shouldThrowExceptionIfCrewWithNoNameProvidedToFindByCrewNameMethod() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> airplaneDao.findByCrewName(INVALID_CREW.getName()));

        assertEquals("Cannot find an airplane because the name is not provided", exception.getMessage());
    }

    @Test
    public void shouldUpdateWithCrewId() throws SQLException {
        populateTestData();

        airplaneDao.updateWithCrewId(TEST_AIRPLANE, CREW.getId());

        Optional<Airplane> result = airplaneDao.findByCrewName("Sokol");
        Airplane expected = Airplane.builder()
                .withId(1L)
                .withCodeName("THD-342")
                .withModel("А319")
                .withManufactureDate(LocalDate.of(2019, 3, 12))
                .withCapacity(168)
                .withFlightRange(3800)
                .withCrewId(3L)
                .build();
        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    public void shouldThrowExceptionIfAirplaneWithNoIdProvidedToUpdateMethod() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> airplaneDao.updateWithCrewId(AIRPLANE, INVALID_CREW.getId()));

        assertEquals("Airplane crewId should not be null", exception.getMessage());
    }

    private static Airplane buildTestAirplane() {
        return Airplane.builder()
                .withId(6L)
                .withCodeName("TKG-344")
                .withModel("А419")
                .withManufactureDate(LocalDate.of(2019, 3, 12))
                .withCapacity(168)
                .withFlightRange(3800)
                .withCrewId(1L)
                .build();
    }

    private static Airplane buildAirplane() {
        return Airplane.builder()
                .withId(1L)
                .withCodeName("THD-342")
                .withModel("А319")
                .withManufactureDate(LocalDate.of(2019, 3, 12))
                .withCapacity(168)
                .withFlightRange(3800)
                .withCrewId(1L)
                .build();
    }

    private static Airplane buildInvalidAirplane() {
        return Airplane.builder()
                .withId(null)
                .withCodeName(null)
                .withModel(null)
                .withManufactureDate(null)
                .withCapacity(168)
                .withFlightRange(3800)
                .withCrewId(null)
                .build();
    }

    private static Crew buildCrewFromDataSet() {
        List<CrewMember> list = new ArrayList<>();
        list.add(CrewMember.builder()
                .withId(11L)
                .withFirstName("Jane")
                .withLastName("Foster")
                .withPosition(PILOT_IN_COMMAND)
                .withBirthday(LocalDate.of(2000, 8, 8))
                .withCitizenship(ITALIAN)
                .build());
        list.add(CrewMember.builder()
                .withId(12L)
                .withFirstName("Bruce")
                .withLastName("Wane")
                .withPosition(PILOT_IN_COMMAND)
                .withBirthday(LocalDate.of(1998, 6, 8))
                .withCitizenship(ITALIAN)
                .build());
        return new Crew(3L, "Sokol", list);
    }

    private static Crew buildInvalidCrewFromDataSet() {
        List<CrewMember> list = new ArrayList<>();
        list.add(CrewMember.builder()
                .withId(11L)
                .withFirstName("Jane")
                .withLastName("Foster")
                .withPosition(PILOT_IN_COMMAND)
                .withBirthday(LocalDate.of(2000, 8, 8))
                .withCitizenship(ITALIAN)
                .build());
        list.add(CrewMember.builder()
                .withId(12L)
                .withFirstName("Bruce")
                .withLastName("Wane")
                .withPosition(PILOT_IN_COMMAND)
                .withBirthday(LocalDate.of(1998, 6, 8))
                .withCitizenship(ITALIAN)
                .build());
        return new Crew(null, null, list);
    }

    private static void createTables() throws SQLException {
        executeQuery(dataSource, CREATE_TABLES_QUERY);
    }

    private static void populateTestData() throws SQLException {
        executeQuery(dataSource, GENERATE_DATA_QUERY);
    }

    private static void executeQuery(DataSource dataSource, String query) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            Statement createTableStatement = connection.createStatement();
            createTableStatement.execute(query);
        }
    }
}