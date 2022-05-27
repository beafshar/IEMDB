package com.iemdb.iemdb.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.iemdb.iemdb.Model.Error.ActorNotFound;
import com.iemdb.iemdb.Model.Error.InvalidRateScore;

import java.beans.ConstructorProperties;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    private final int id;
    private final String name;
    private final String summary;
    private final String releaseDate;
    private final String director;
    List<String> writers = new ArrayList<>();
    List<String> genres = new ArrayList<>();
    List<Integer> cast = new ArrayList<>();
    private final double imdbRate;
    private final long duration;
    private final int ageLimit;
    private double rating = 0;
    private double ratingCount = 0;
    private final List<Comment> comments = new ArrayList<>();
    private final Map<String, Integer> map = new HashMap<>();
    private final String image;
    private final String coverImage;

    @ConstructorProperties({"id","name","summary", "releaseDate", "director", "writers", "genres", "cast", "imdbRate", "duration", "ageLimit", "image", "coverImage"})
    @JsonCreator
    public Movie(@JsonProperty(value = "id", required = true) int id, @JsonProperty(value = "name", required = true) String name,
                 @JsonProperty(value = "summary", required = true) String summary, @JsonProperty(value = "releaseDate", required = true) String releaseDate,
                 @JsonProperty(value = "director", required = true) String director, @JsonProperty(value = "writers", required = true) ArrayNode writers,
                 @JsonProperty(value = "genres", required = true) ArrayNode genres, @JsonProperty(value = "cast", required = true) ArrayNode cast,
                 @JsonProperty(value = "imdbRate", required = true) double imdbRate, @JsonProperty(value = "duration", required = true) long duration,
                 @JsonProperty(value = "ageLimit", required = true) int ageLimit, @JsonProperty(value = "image", required = true)  String image,
                 @JsonProperty(value = "coverImage", required = true) String coverImage) throws ActorNotFound {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.director = director;
        this.image = image;
        this.coverImage = coverImage;
        for(int i = 0; i < writers.size(); i++){
            this.writers.add(writers.get(i).asText());
        }
        for(int i = 0; i < genres.size(); i++){
            this.genres.add(genres.get(i).asText());
        }
        for(int i = 0; i < cast.size(); i++){
            this.cast.add(cast.get(i).intValue());
            IEMDBController.actorHandler.findActor(cast.get(i).intValue()).addMovie(this);
        }
        this.imdbRate = imdbRate;
        this.duration = duration;
        this.ageLimit = ageLimit;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getSummary() {return this.summary; }
    public String getReleaseDate() {return this.releaseDate; }
    public String getDirector() {return this.director; }
    public List<String> getWriters() {return this.writers; }
    public List<String> getGenres() {return this.genres; }
    public List<Integer> getCast() { return this.cast; }
    public double getImdbRate() {return this.imdbRate;}
    public long getDuration() {return this.duration;}
    public int getAgeLimit() {return this.ageLimit;}
    public String getImage() {return this.image;}
    public String getCoverImage() {return this.coverImage;}
    public ArrayList<Comment> getComment() {return (ArrayList<Comment>) this.comments;}

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void rateMovie(String userEmail, int score) throws InvalidRateScore {
        if (score > 10 || score <= 0) {
            throw new InvalidRateScore();
        }
        if(this.map.containsKey(userEmail)) {
            if (this.ratingCount == 1)
                this.rating = 0;
            else
                this.rating = (this.rating*this.ratingCount - this.map.get(userEmail))/(this.ratingCount - 1);
            this.ratingCount--;
        }
        this.map.put(userEmail, score);
        this.ratingCount += 1;
        this.rating = (this.rating*(this.ratingCount-1) + score)/this.ratingCount;
        this.rating = roundToHalf(this.rating);
    }
    public double getRating() {return this.rating;}
    public double getRatingCount() {return this.ratingCount;}

    public ArrayList<Actor> getActors() throws ActorNotFound {
        ArrayList<Actor> actors = new ArrayList<>();
        for(int i = 0; i < this.cast.size(); i++) {
            actors.add(IEMDBController.actorHandler.findActor(this.cast.get(i)));
        }
        return actors;
    }

    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }
}