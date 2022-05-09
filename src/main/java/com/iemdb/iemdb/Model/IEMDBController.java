package com.iemdb.iemdb.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemdb.iemdb.Model.Error.MovieNotFound;
import com.iemdb.iemdb.Model.Error.UserNotFound;
import com.iemdb.iemdb.Repository.ActorRepository;
//import com.iemdb.iemdb.Repository.CommentRepository;
//import com.iemdb.iemdb.Repository.MovieRepository;
//import com.iemdb.iemdb.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;



@Service
@AllArgsConstructor
public class IEMDBController {
    private static final String SERVICE_API = "http://138.197.181.131:5000";
    private static final String MOVIES_API = SERVICE_API + "/api/v2/movies";
    private static final String ACTORS_API = SERVICE_API + "/api/v2/actors";
    private static final String USERS_API = SERVICE_API + "/api/users";
    private static final String COMMENTS_API = SERVICE_API + "/api/comments";

//    public static final ActorHandler actorHandler = new ActorHandler();
//    public static final UserHandler userHandler = new UserHandler();
//    public static final MovieHandler movieHandler = new MovieHandler();
//    public static final CommentHandler commentHandler = new CommentHandler();

    private ActorRepository actorRepo;
//    private MovieRepository movieRepo;
//    private UserRepository userRepo;
//    private CommentRepository commentRepo;


//    private User active_user = null;

//    private static IEMDBController instance;


//    private IEMDBController() throws IOException, InterruptedException, MovieNotFound {
//        setDatasets();
//    }

//    public static IEMDBController getInstance() throws IOException, InterruptedException, MovieNotFound {
//        if (instance == null)
//            instance = new IEMDBController();
//        return instance;
//    }

//    public User getActive_user() {
//        return active_user;
//    }
//
//    public User setActive_user(String active_user, String password) throws UserNotFound {
//        this.active_user = userHandler.findUser(active_user, password);
//        return userHandler.findUser(active_user, password);
//    }

    public void setDatasets() throws IOException, InterruptedException, MovieNotFound {
        System.out.println("gggggggggggg");
        actorRepo.saveAll(getActorsFromAPI());
        System.out.println("hhhhhhhhhhhhhhh");
        System.out.println(actorRepo.findAll());
//        actorHandler.setActors(getActorsFromAPI());
//        movieHandler.setMovies(getMoviesFromAPI());
//        userHandler.setUsers(getUsersFromAPI());
//        commentHandler.setComments(getCommentsFromAPI());
    }

//    public static Movie[] getMoviesFromAPI() throws IOException, InterruptedException {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(MOVIES_API))
//                .build();
//        HttpResponse<String> response = client.send(request,
//                HttpResponse.BodyHandlers.ofString());
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(response.body(), Movie[].class);
//    }
//
    @PostConstruct
    public static List<Actor> getActorsFromAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ACTORS_API))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        Actor[] actors = objectMapper.readValue(response.body(), Actor[].class);
        return Arrays.stream(actors).toList();
    }
//
//    public static User[] getUsersFromAPI() throws IOException, InterruptedException {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(USERS_API))
//                .build();
//        HttpResponse<String> response = client.send(request,
//                HttpResponse.BodyHandlers.ofString());
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(response.body(), User[].class);
//
//    }
//    public static Comment[] getCommentsFromAPI() throws IOException, InterruptedException {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(COMMENTS_API))
//                .build();
//        HttpResponse<String> response = client.send(request,
//                HttpResponse.BodyHandlers.ofString());
//        ObjectMapper objectMapper = new ObjectMapper();
//        return objectMapper.readValue(response.body(), Comment[].class);
//    }

}
