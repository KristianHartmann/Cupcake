package dat.startcode.persistence;

import dat.startcode.entities.User;
import dat.startcode.exceptions.DatabaseException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserMapper implements IUserMapper
{
    ConnectionPool connectionPool;

    public UserMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }

    @Override
    public User login(String email, String password) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");

        User user = null;

        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    String role = rs.getString("role");
                    user = new User(email, password, role);
                } else
                {
                    throw new DatabaseException("Fejl i brugernavn eller password");
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl under indlogning. Der er noget galt med databasen");
        }
        return user;
    }

    @Override
    public User createUser(String email, String password, String role) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        User user;
        String sql = "insert into user (email, password, role) values (?,?,?)";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, email);
                ps.setString(2, password);
                ps.setString(3, role);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1)
                {
                    user = new User(email, password, role);
                } else
                {
                    throw new DatabaseException("Brugeren med email = " + email + " kunne ikke oprettes i databasen");
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Kunne ikke indsætte bruger i databasen");
        }
        return user;
    }


}
