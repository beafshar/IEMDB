package com.myservlet.Model;

import java.time.LocalDate;
import java.util.*;

import com.myservlet.Model.Error.AgeLimitError;
import com.myservlet.Model.Error.MovieAlreadyExists;
import com.myservlet.Model.Error.MovieNotFound;

public class User {
    private final String email;
    private final String password;
    private final String name;
    private final String nickname;
    private final String birthDate;
    private final List<Integer> WatchList = new ArrayList<>();

    public User(String email,  String password, String nickname, String name, String birthDate)
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

    public void addToWatchList(int movieId) throws MovieAlreadyExists, AgeLimitError, MovieNotFound {
        Movie movie = MovieHandler.findMovie(movieId);
        if(movie != null) {
            if(calculateUserAge() >= movie.getAgeLimit()) {
                if(WatchList.contains(movie.getId()))
                    throw new MovieAlreadyExists();
                WatchList.add(movie.getId());
            }
            throw new AgeLimitError();
        }
        throw new MovieNotFound();
    }

    public void removeFromWatchList(int movieId) throws MovieNotFound {
        if (WatchList.contains(movieId))
            WatchList.remove((Integer) movieId);
        throw new MovieNotFound();
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
