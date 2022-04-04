package dat.startcode.persistence;

import dat.startcode.entities.User;
import dat.startcode.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest
{
    private final static String USER = "root";
    private final static String PASSWORD = "root";
    private final static String URL = "jdbc:mysql://localhost:3306/bibliotek_test?serverTimezone=CET&allowPublicKeyRetrieval=true&useSSL=false";

    private static ConnectionPool connectionPool;
    private static UserMapper userMapper;

    @BeforeAll
    public static void setUpClass() {
            connectionPool = new ConnectionPool(USER, PASSWORD, URL);
            userMapper = new UserMapper(connectionPool);
    }

    @BeforeEach
    void setUp()
    {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement() ) {
                // Remove all rows from all tables
                stmt.execute("delete from bruger");
                // Indsæt et par brugere
                stmt.execute("insert into bruger (email, kodeord, rolle) " +
                        "values ('a@a.dk','1234','laaner'),('b@b.dk','1234','admin'), ('c@c.dk','1234','laaner')");
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void testConnection() throws SQLException
    {
        Connection connection = connectionPool.getConnection();
        assertNotNull(connection);
        if (connection != null)
        {
            connection.close();
        }
    }

    @Test
    void login() throws DatabaseException
    {
        User forventetUser = new User("a@a.dk","1234","laaner");
        User faktiskUser = userMapper.login("a@a.dk","1234");
        assertEquals(forventetUser, faktiskUser);
    }

    @Test
    void invalidPasswordLogin() throws DatabaseException
    {
        assertThrows(DatabaseException.class, () -> userMapper.login("a@a.dk","123"));
    }

    @Test
    void invalidEmailLogin() throws DatabaseException
    {
        assertThrows(DatabaseException.class, () -> userMapper.login("a@b.dk","1234"));
    }

    @Test
    void opretBruger() throws DatabaseException
    {
        User nyUser = userMapper.createUser("ole@ole.dk", "1234", "laaner");
        User logPaaUser = userMapper.login("ole@ole.dk","1234");
        User forventetUser = new User("ole@ole.dk", "1234", "laaner");
        assertEquals(forventetUser, nyUser);
        assertEquals(forventetUser, logPaaUser);

    }
}