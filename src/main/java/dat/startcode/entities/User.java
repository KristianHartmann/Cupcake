package dat.startcode.entities;

import java.util.Objects;

public class User
{
    private String email;
    private String password;
    private String role;

    public User(String email, String password, String rolle)
    {
        this.email = email;
        this.password = password;
        this.role = rolle;
    }



    @Override
    public String toString()
    {
        return "Bruger{" +
                "email='" + email + '\'' +
                ", kodeord='" + password + '\'' +
                ", rolle='" + role + '\'' +
                '}';
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getEmail().equals(user.getEmail()) && getPassword().equals(user.getPassword()) &&
                getRole().equals(user.getRole());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getEmail(), getPassword(), getRole());
    }
}
