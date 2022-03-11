package com.myservlet.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myservlet.Model.Movie;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;

public class Actor {
    private int id;
    private String name;
    private String birthDate;
    private String nationality;
    private List<Movie> movies = new ArrayList<>();

    @ConstructorProperties({"id","name","birthDate","nationality"})
    @JsonCreator
    public Actor(@JsonProperty(value = "id", required = true) int id,
                 @JsonProperty(value = "name", required = true) String name,
                 @JsonProperty(value = "birthDate", required = true) String birthDate,
                 @JsonProperty(value = "nationality", required = true) String nationality)
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
    public void addMovie(Movie movie)
    {
        this.movies.add(movie);
    }
    public List<Movie> getMovies()
    {
        return this.movies;
    }
}
