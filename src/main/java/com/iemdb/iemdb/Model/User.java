package com.iemdb.iemdb.Model;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iemdb.iemdb.Model.Error.AgeLimitError;
import com.iemdb.iemdb.Model.Error.MovieAlreadyExists;
import com.iemdb.iemdb.Model.Error.MovieNotFound;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String birthDate;
    private final List<Integer> WatchList = new ArrayList<>();
    private String token;

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
    public String getBirthdate() {return this.birthDate;}
    public String getNickname() {return this.nickname;}
    public ArrayList<Movie> getWatchlist() throws MovieNotFound {
        ArrayList<Movie> watch = new ArrayList<>();
        for(int id : WatchList) {
            watch.add(MovieHandler.findMovie(id));
        }
        return watch;
    }
    public String getPassword() {return this.password;}

    public void updateUser(String email, String password, String name, String nickname, String birthDate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.birthDate = birthDate;
    }

    private int calculateUserAge() {
        LocalDate birth = LocalDate.parse(this.birthDate);
        LocalDate curDate = LocalDate.now();
        return curDate.getYear() - birth.getYear();
    }

    public void addToWatchList(int movieId) throws MovieAlreadyExists, AgeLimitError, MovieNotFound {
        Movie movie = MovieHandler.findMovie(movieId);
        if(movie != null) {
            if(calculateUserAge() >= movie.getAgeLimit()) {
                if(WatchList.contains(movie.getId()))
                    throw new MovieAlreadyExists();
                else WatchList.add(movie.getId());
            }
            else throw new AgeLimitError();
        }
        else throw new MovieNotFound();
    }

    public void removeFromWatchList(int movieId) throws MovieNotFound {
        if (WatchList.contains(movieId))
            WatchList.remove((Integer) movieId);
        else throw new MovieNotFound();
    }

    public List<Movie> getRecommendationList() throws MovieNotFound {
        List<Movie> recommended_movies = new ArrayList<>();
        TreeMap<Double, Movie> movie_score = new TreeMap<>();
        for(Movie movie: IEMDBController.movieHandler.movies.values()) {
            if(!this.WatchList.contains(movie.getId())){
                double score = movie.getRating() + movie.getImdbRate();
                int genre_similarity = 0;
                for(Integer id: this.WatchList){
                    Movie m = IEMDBController.movieHandler.findMovie(id);
                    genre_similarity += (int) m.getGenres().stream().filter(movie.getGenres()::contains).count();
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
