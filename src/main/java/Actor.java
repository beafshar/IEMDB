import java.util.Date;



public class Actor {
    private int id;
    private String name;
    private String birthDate;
    private String nationality;

    public Actor()
    {

    }
    public Actor(int _id, String _name, String _birthDate, String _nationality)
    {
        id = _id;
        name = _name;
        birthDate = _birthDate;
        nationality = _nationality;
    }

    public void updateActor(int _id, String _name, String _birthDate, String _nationality)
    {
        id = _id;
        name = _name;
        birthDate = _birthDate;
        nationality = _nationality;
    }

    public String getNationality() {
        return nationality;
    }
    public String getBirthDate()
    {
        return birthDate;
    }
    public String getName()
    {
        return name;
    }
    public int getId()
    {
        return id;
    }
}
