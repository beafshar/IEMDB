package com.iemdb.iemdb.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemdb.iemdb.Model.Error.MovieNotFound;
import com.iemdb.iemdb.Model.Error.UserNotFound;
import com.iemdb.iemdb.Repository.ActorRepository;
//import com.iemdb.iemdb.Repository.CommentRepository;
//import com.iemdb.iemdb.Repository.MovieRepository;
//import com.iemdb.iemdb.Repository.UserRepository;
import com.iemdb.iemdb.Repository.CommentRepository;
import com.iemdb.iemdb.Repository.MovieRepository;
import com.iemdb.iemdb.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
//import org.json.simple.JSONArray;
//import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


@Service
public class IEMDBController {
    private static final String SERVICE_API = "http://138.197.181.131:5000";
    private static final String MOVIES_API = SERVICE_API + "/api/v2/movies";
    private static final String ACTORS_API = SERVICE_API + "/api/v2/actors";
    private static final String USERS_API = SERVICE_API + "/api/users";
    private static final String COMMENTS_API = SERVICE_API + "/api/comments";

    private ActorRepository actorRepo;
    private MovieRepository movieRepo;
    private UserRepository userRepo;
    private CommentRepository commentRepo;

    public IEMDBController(ActorRepository actorRepo, MovieRepository movieRepo, UserRepository userRepo, CommentRepository commentRepo) {
        this.actorRepo = actorRepo;
        this.movieRepo = movieRepo;
        this.userRepo = userRepo;
        this.commentRepo = commentRepo;
    }

    private List<Actor> actors;
    private List<Movie> movies;
    private User[] users;
    private Comment[] comments;


    private static User active_user;

    public static User getActive_user() {
        return active_user;
    }

    public User setActive_user(String active_user, String password) throws UserNotFound {
        List<User> user = userRepo.findByUsernameAndPassword(active_user, password);
        if (user.size() == 0) {
            throw new UserNotFound();
        }
        this.active_user = user.get(0);
        return user.get(0);
    }

    @PostConstruct
    public void setDatasets() throws IOException, InterruptedException, MovieNotFound {
        for (Movie movie: getMoviesFromAPI()){
            movieRepo.save(movie);
        }

        for (Actor actor: getActorsFromAPI()){
            actor.setMovies(movieRepo.findAllByCastId(actor.getId()));
            actorRepo.save(actor);
        }

        for (User user: getUsersFromAPI()){
            userRepo.save(user);
        }

        for (Comment comment: getCommentsFromAPI()){
            commentRepo.save(comment);
            this.movies.get(comment.getMovieId()).addComment(comment);
            movieRepo.save(this.movies.get(comment.getMovieId()));
        }
    }

    public List<Movie> getMoviesFromAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(MOVIES_API))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        this.movies = Arrays.asList(objectMapper.readValue(response.body(), Movie[].class));
        return this.movies;
    }

    public List<Actor> getActorsFromAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ACTORS_API))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        this.actors = Arrays.asList(objectMapper.readValue(response.body(), Actor[].class));
        return this.actors;
    }

    public User[] getUsersFromAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USERS_API))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        this.users = objectMapper.readValue(response.body(), User[].class);
        return this.users;

    }
    public Comment[] getCommentsFromAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(COMMENTS_API))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        this.comments = objectMapper.readValue(response.body(), Comment[].class);
        return this.comments;
    }

}
