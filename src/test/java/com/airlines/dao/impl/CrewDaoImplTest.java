package com.airlines.dao.impl;

import com.airlines.dao.CrewDao;
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

import static com.airlines.model.Citizenship.BRAZILIAN;
import static com.airlines.model.Citizenship.ITALIAN;
import static com.airlines.model.Position.PILOT_IN_COMMAND;
import static org.junit.Assert.*;

public class CrewDaoImplTest {
    private static final String SCHEMA_SQL_PATH = "src/test/resources/sql/schema.sql";
    private static final String DATA_SQL_PATH = "src/test/resources/sql/test_data.sql";
    private static final String CREATE_TABLES_QUERY = JdbcUtil.getSqlQueryString(SCHEMA_SQL_PATH);
    private static final String GENERATE_DATA_QUERY = JdbcUtil.getSqlQueryString(DATA_SQL_PATH);

    private static final CrewMember CREW_MEMBER = buildTestCrewMember();
    private static final Crew CREW = buildCrewFromDataSet();
    private static final Crew INVALID_CREW = buildInvalidCrewFromDataSet();

    private static CrewDao crewDao;

    private static DataSource dataSource;

    @BeforeClass
    public static void init() {
        dataSource = TestDatasourceProvider.createTestDatasource();
        crewDao = new CrewDaoImpl(dataSource);
    }

    @Before
    public void dataBaseInit() throws SQLException {
        createTables();
    }

    @Test
    public void shouldThrowExceptionIfInvalidArgumentCrewMemberProvidedToAddMethod() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> crewDao.add(null, null));
        assertEquals("Valid crewMember entity should be provided", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfInvalidArgumentCrewProvidedToAddMethod() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> crewDao.add(CREW_MEMBER, null));
        assertEquals("Valid crew entity should be provided", exception.getMessage());
    }

    @Test
    public void shouldCorrectlyAddCrewMemberToCrew() throws SQLException {
        populateTestData();
        crewDao.add(CREW_MEMBER, CREW);
        List<CrewMember> list = crewDao.getByCrewId(3L);
        assertTrue(list.contains(CREW_MEMBER));
    }

    @Test
    public void shouldGetCrewMembersByCrewId() throws SQLException {
        populateTestData();
        List<CrewMember> list = crewDao.getByCrewId(3L);
        assertEquals(1, list.size());
    }

    @Test
    public void shouldThrowExceptionIfCrewIsNotProvidedForGetByIdMethod() throws SQLException {
        Crew invalidCrew = buildInvalidCrewFromDataSet();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> crewDao.getByCrewId(invalidCrew.getId()));
        assertEquals("Cannot find crew members for crew without id", exception.getMessage());
    }

    @Test
    public void getByCrewName() throws SQLException {
        populateTestData();
        List<CrewMember> list = crewDao.getByCrewName("Vityaz");
        assertEquals(3, list.size());
    }

    @Test
    public void shouldRemoveCrewMemberFromCrew() throws SQLException {
        populateTestData();
        crewDao.add(CREW_MEMBER, CREW);
        List<CrewMember> list = crewDao.getByCrewId(3L);
        assertTrue(list.contains(CREW_MEMBER));
        crewDao.removeCrewMemberFromCrew(CREW, CREW_MEMBER);
        List<CrewMember> listAfterRemove = crewDao.getByCrewId(3L);
        assertFalse(listAfterRemove.contains(CREW_MEMBER));
    }

    @Test
    public void shouldThrowExceptionIfCrewIsNotProvidedForRemoveMethod() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> crewDao.removeCrewMemberFromCrew(INVALID_CREW, CREW_MEMBER));
        assertEquals("Valid crew entity should be provided", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionIfCrewMemberIsNotProvidedForRemoveMethod() {
        Crew crew = buildCrewFromDataSet();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> crewDao.removeCrewMemberFromCrew(crew, null));
        assertEquals("Valid crewMember entity should be provided", exception.getMessage());
    }

    private static CrewMember buildTestCrewMember() {
        return CrewMember.builder()
                .withId(1L)
                .withFirstName("Igor")
                .withLastName("Shpack")
                .withPosition(PILOT_IN_COMMAND)
                .withBirthday(LocalDate.of(1983, 12, 8))
                .withCitizenship(BRAZILIAN)
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
        return new Crew(null, "X-Men", list);
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