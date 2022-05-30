package com.iemdb.iemdb.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Actor {
    @Id
    private int id;
    private String name;
    private String birthDate;
    private String nationality;
    private String image;
    @ManyToMany(fetch= FetchType.EAGER)
    private List<Movie> movies = new ArrayList<>();
    private Integer numberOfMovies = 0;
    @ConstructorProperties({"id","name","birthDate","nationality","image"})
    @JsonCreator
    public Actor(@JsonProperty(value = "id", required = true) int id,
                 @JsonProperty(value = "name", required = true) String name,
                 @JsonProperty(value = "birthDate", required = true) String birthDate,
                 @JsonProperty(value = "nationality", required = true) String nationality,
                 @JsonProperty(value = "image", required = true) String image)
    {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.image = image;
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
    public String getImage() { return image; }
    public void addMovie(Movie movie)
    {
        this.movies.add(movie);
        this.numberOfMovies++;
    }
    public List<Movie> getMovies() { return this.movies;}
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
