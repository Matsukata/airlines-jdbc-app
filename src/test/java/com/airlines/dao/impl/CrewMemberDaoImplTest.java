package com.airlines.dao.impl;

import com.airlines.dao.CrewMemberDao;
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
import java.util.Optional;

import static com.airlines.model.Citizenship.BRAZILIAN;
import static com.airlines.model.Citizenship.ITALIAN;
import static com.airlines.model.Citizenship.NORWEGIAN;
import static com.airlines.model.Position.AIRCRAFT_PILOT;
import static com.airlines.model.Position.FIRST_OFFICER;
import static com.airlines.model.Position.FLIGHT_ATTENDANT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class CrewMemberDaoImplTest {
    private static final String SCHEMA_SQL_PATH = "src/test/resources/sql/schema.sql";
    private static final String DATA_SQL_PATH = "src/test/resources/sql/test_data.sql";
    private static final String CREATE_TABLES_QUERY = JdbcUtil.getSqlQueryString(SCHEMA_SQL_PATH);
    private static final String GENERATE_DATA_QUERY = JdbcUtil.getSqlQueryString(DATA_SQL_PATH);
    private static final CrewMember CREW_MEMBER = buildTestCrewMember();
    private static final CrewMember TEST_DATA_CREW_MEMBER = buildCrewMemberFromDataset();

    private static CrewMemberDao crewMemberDao;

    private static DataSource dataSource;

    @BeforeClass
    public static void init() {
        dataSource = TestDatasourceProvider.createTestDatasource();
        crewMemberDao = new CrewMemberDaoImpl(dataSource);
    }

    @Before
    public void dataBaseInit() throws SQLException {
        createTables();
    }

    @Test
    public void shouldFindCorrectCrewMember() throws SQLException {
        populateTestData();
        Optional<CrewMember> crewMember = crewMemberDao.findById(TEST_DATA_CREW_MEMBER.getId());

        assertTrue(crewMember.isPresent());
        CrewMember actual = crewMember.get();
        assertEquals(TEST_DATA_CREW_MEMBER, actual);
    }

    @Test
    public void shouldReturnEmptyOptionalIfCrewMemberWasNotFound() throws SQLException {
        populateTestData();
        Optional<CrewMember> crewMember = crewMemberDao.findById(15L);
        assertTrue(crewMember.isEmpty());
    }

    @Test
    public void shouldThrowExceptionIfInvalidArgumentProvidedToFindByIdMethod() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> crewMemberDao.findById(null));
        assertEquals("Cannot find the member because id is not provided", exception.getMessage());
    }

    @Test
    public void shouldCorrectlySaveCrewMemberToDatabase() {
        Optional<CrewMember> savedCrewMember = crewMemberDao.save(CREW_MEMBER);
        assertTrue(savedCrewMember.isPresent());
        assertEquals(CREW_MEMBER, savedCrewMember.get());

        CrewMember foundEntity = crewMemberDao.findById(CREW_MEMBER.getId()).get();
        assertEquals(CREW_MEMBER, foundEntity);
    }

    @Test
    public void shouldUpdateCrewMember() throws SQLException {
        populateTestData();
        CrewMember expected = CrewMember.builder()
                .withId(1L)
                .withFirstName("Ivanna")
                .withLastName("Armstrong")
                .withCitizenship(BRAZILIAN)
                .withPosition(FLIGHT_ATTENDANT)
                .withBirthday(LocalDate.of(1990, 12, 7))
                .build();

        crewMemberDao.update(expected);

        Optional<CrewMember> result = crewMemberDao.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(expected, result.get());
    }

    @Test
    public void shouldThrowExceptionIfIdIsNotProvidedForUpdateMethod() {
        CrewMember crewMember = CrewMember.builder().build();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> crewMemberDao.update(crewMember));
        assertEquals("CrewMember id should not be null", exception.getMessage());
    }

    private static CrewMember buildTestCrewMember() {
        return CrewMember.builder()
                .withId(1L)
                .withFirstName("John")
                .withLastName("Smith")
                .withCitizenship(ITALIAN)
                .withPosition(AIRCRAFT_PILOT)
                .withBirthday(LocalDate.of(2002, 10, 5))
                .build();
    }

    private static CrewMember buildCrewMemberFromDataset() {
        return CrewMember.builder()
                .withId(7L)
                .withFirstName("Marat")
                .withLastName("Abakulov")
                .withCitizenship(NORWEGIAN)
                .withPosition(FIRST_OFFICER)
                .withBirthday(LocalDate.of(1994, 3, 8))
                .build();
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