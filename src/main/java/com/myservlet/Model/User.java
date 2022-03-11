package com.myservlet.Model;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import Model.Error.AgeLimitError;
import Model.Error.MovieAlreadyExists;
import Model.Error.MovieNotFound;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class User {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String birthDate;
    private List<Integer> WatchList = new ArrayList<>();

    @ConstructorProperties({"email","password","nickname","name","birthDate"})
    @JsonCreator
    public User(@JsonProperty(value = "email", required = true) String email,
                @JsonProperty(value = "password", required = true) String password,
                @JsonProperty(value = "nickname", required = true) String nickname,
                @JsonProperty(value = "name", required = true) String name,
                @JsonProperty(value = "birthDate", required = true) String birthDate)
    {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.birthDate = birthDate;
    }

    public String getEmail() { return this.email; }
    public String getName() { return this.name; }
    public String getBirthDate() {return this.birthDate;}
    public String getNickname() {return this.nickname;}
    public List<Integer> getWatch() {return this.WatchList;}

    private int calculateUserAge() {
        LocalDate birth = LocalDate.parse(this.birthDate);
        LocalDate curDate = LocalDate.now();
        return curDate.getYear() - birth.getYear();
    }

    public ObjectNode addToWatchList(int movieId) throws MovieAlreadyExists, AgeLimitError, MovieNotFound {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        Movie movie = MovieHandler.returnMovieObjectGivenId(movieId);
        if(movie != null) {
            if(calculateUserAge() >= movie.getAgeLimit()) {
                if(WatchList.contains(movie.getId())) {
                    throw new MovieAlreadyExists();
                }
                WatchList.add(movie.getId());
                response.put("success", true);
                response.put("data", "movie added to watchlist successfully");
                return response;
            }
            throw new AgeLimitError();
        }
            throw new MovieNotFound();
    }

    public ObjectNode removeFromWatchList(int movieId) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        if (WatchList.contains(movieId)) {
            WatchList.remove((Integer) movieId);
            response.put("success", true);
            response.put("data", "movie removed from watchlist successfully");
            return response;
        }
        return MovieHandler.MovieNotFound();
    }

    public ObjectNode getWatchList() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        ObjectNode root = objectMapper.createObjectNode();
        ArrayNode arrayNode = objectMapper.createArrayNode();
        for(Integer movie : WatchList) {
            Movie m = MovieHandler.returnMovieObjectGivenId(movie);
            ObjectNode mov = objectMapper.createObjectNode();
            mov.put("movieId", m.getId());
            mov.put("name", m.getName());
            mov.put("director", m.getDirector());
            ArrayNode jsonArray = objectMapper.createArrayNode();
            for(String g : m.getGenres()) {
                jsonArray.add(g);
            }
            mov.putArray("genres").addAll(jsonArray);
            mov.put("rating", m.getRating());
            arrayNode.add(mov);
        }
        root.putArray("WatchList").addAll(arrayNode);
        response.put("success", true);
        response.set("data", root);
        return response;
    }

    public List<Movie> getRecommendationList() {
        List<Movie> recommended_movies = new ArrayList<>();
        TreeMap<Double, Movie> movie_score = new TreeMap<>();
        for(Movie movie: IEMDBController.movieHandler.movies.values()) {
            if(this.WatchList.contains(movie.getId()) == false){
                double score = movie.getRating() + movie.getImdbRate();
                int genre_similarity = 0;
                for(Integer id: this.WatchList){
                    Movie m = IEMDBController.movieHandler.movies.get(id);
                    genre_similarity += m.getGenres().stream().filter(movie.getGenres()::contains).collect(Collectors.toList()).size();
                }
                score += genre_similarity*3;
                movie_score.put(score, movie);
            }
        }
        for(int i = 0; i < 3; i++) {
            recommended_movies.add(movie_score.lastEntry().getValue());
            movie_score.remove(movie_score.lastKey());
        }
        return recommended_movies;
    }
}
