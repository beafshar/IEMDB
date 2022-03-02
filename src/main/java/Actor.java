import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.ConstructorProperties;

public class Actor {
    private int id;
    private String name;
    private String birthDate;
    private String nationality;

    @ConstructorProperties({"id","name","birthDate","nationality"})
    @JsonCreator
    public Actor(int id, String name, String birthDate, @JsonProperty(value = "nationality", required = true) String nationality)
    {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
    }

    public String getNationality() {
        return this.nationality;
    }
    public String getBirthDate()
    {
        return this.birthDate;
    }
    public String getName()
    {
        return this.name;
    }
    public int getId()
    {
        return this.id;
    }
}
