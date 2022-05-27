package com.iemdb.iemdb.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Actor {
    private final int id;
    private final String name;
    private final String birthDate;
    private final String nationality;
    private final String image;
    private final List<Integer> movies = new ArrayList<>();
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
    public Integer getNumberOfMovies() { return numberOfMovies; }
    public void addMovie(Movie movie)
    {
        this.movies.add(movie.getId());
        this.numberOfMovies++;
    }
    public ArrayList<Integer> getMovies() { return (ArrayList<Integer>) this.movies;}
}
