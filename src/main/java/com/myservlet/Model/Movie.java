package com.myservlet.Model;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.myservlet.Model.Error.ActorNotFound;
import com.myservlet.Model.Error.InvalidRateScore;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Movie(int id, String name,  String summary, String releaseDate,  String director, ArrayNode writers, ArrayNode genres, ArrayNode cast,
                 double imdbRate, long duration, int ageLimit) throws ActorNotFound {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.director = director;
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
        this.rating = Double.parseDouble(new DecimalFormat("##.#").format(this.rating));
    }
    public double getRating() {return this.rating;}
    public double getRatingCount() {return this.ratingCount;}
    public List<Comment> getComments() {return this.comments;}
}