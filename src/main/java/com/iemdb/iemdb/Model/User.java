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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    private  String email;
    private  String password;
    private  String name;
    private  String nickname;
    private  String birthDate;
    @OneToMany(fetch= FetchType.EAGER)
    private  List<Movie> WatchList = new ArrayList<>();

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
    public ArrayList<Movie> getWatchlist() throws MovieNotFound {
        ArrayList<Movie> watch = new ArrayList<>();
        for(Movie movie : WatchList) {
            watch.add(movie);
        }
        return watch;
    }
    public String getPassword() {return this.password;}

    private int calculateUserAge() {
        LocalDate birth = LocalDate.parse(this.birthDate);
        LocalDate curDate = LocalDate.now();
        return curDate.getYear() - birth.getYear();
    }

    public void addToWatchList(Movie movie) throws MovieAlreadyExists, AgeLimitError, MovieNotFound {
        if(movie != null) {
            if(calculateUserAge() >= movie.getAgeLimit()) {
                if(WatchList.contains(movie.getId()))
                    throw new MovieAlreadyExists();
                else WatchList.add(movie);
            }
            else throw new AgeLimitError();
        }
        else throw new MovieNotFound();
    }

    public void removeFromWatchList(Movie movie) throws MovieNotFound {
        if (WatchList.contains(movie))
            WatchList.remove(movie);
        else throw new MovieNotFound();
    }

    public List<Movie> getRecommendationList(List<Movie> movies) throws MovieNotFound {
        List<Movie> recommended_movies = new ArrayList<>();
        TreeMap<Double, Movie> movie_score = new TreeMap<>();
        for(Movie movie: movies) {
            if(!this.WatchList.contains(movie.getId())){
                double score = movie.getRating() + movie.getImdbRate();
                int genre_similarity = 0;
                for(Movie m: this.WatchList){
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
