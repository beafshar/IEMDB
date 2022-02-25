import java.util.SplittableRandom;

public class User {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String birthDate;

    public User()
    {

    }

    public User(String _email, String _password, String _name, String _nickname, String _birthDate)
    {
        email = _email;
        password = _password;
        name = _name;
        nickname = _nickname;
        birthDate = _birthDate;
    }

    public String getEmail()
    {
        return email;
    }
    public String getName()
    {
        return name;
    }
}
