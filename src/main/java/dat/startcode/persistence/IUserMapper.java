package dat.startcode.persistence;

import dat.startcode.entities.User;
import dat.startcode.exceptions.DatabaseException;

public interface IUserMapper
{
    public User login(String email, String password) throws DatabaseException;
    public User createUser(String email, String password, String role) throws DatabaseException;
}
