package com.iemdb.iemdb.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iemdb.iemdb.Model.Error.MovieNotFound;
import com.iemdb.iemdb.Model.Error.UserNotFound;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

public class IEMDBController {
    private static final String SERVICE_API = "http://138.197.181.131:5000";
    private static final String MOVIES_API = SERVICE_API + "/api/v2/movies";
    private static final String ACTORS_API = SERVICE_API + "/api/v2/actors";
    private static final String USERS_API = SERVICE_API + "/api/users";
    private static final String COMMENTS_API = SERVICE_API + "/api/comments";

    public static final ActorHandler actorHandler = new ActorHandler();
    public static final UserHandler userHandler = new UserHandler();
    public static final MovieHandler movieHandler = new MovieHandler();
    public static final CommentHandler commentHandler = new CommentHandler();

    public static String KEY = "iemdb1401iemdb1401iemdb1401iemdb1401";

    public String token = null;

    private User active_user = null;

    private static IEMDBController instance;

    private IEMDBController() throws IOException, InterruptedException, MovieNotFound {
        setDatasets();
    }

    public static IEMDBController getInstance() throws IOException, InterruptedException, MovieNotFound {
        if (instance == null)
            instance = new IEMDBController();
        return instance;
    }

    public User getActive_user() {
        return active_user;
    }

    public User setActive_user(String active_user, String password) throws UserNotFound {
        this.active_user = userHandler.findUser(active_user, password);
        this.token = this.getToken(this.active_user);
        return userHandler.findUser(active_user, password);
    }
    public User setActive_user_from_git(String active_user) throws UserNotFound {
        this.active_user = userHandler.findUserFromGit(active_user);
        return userHandler.findUserFromGit(active_user);
    }

    public void setActive_user_null() {
        this.active_user = null;
    }

    public static void setDatasets() throws IOException, InterruptedException, MovieNotFound {
        actorHandler.setActors(getActorsFromAPI());
        movieHandler.setMovies(getMoviesFromAPI());
        userHandler.setUsers(getUsersFromAPI());
        commentHandler.setComments(getCommentsFromAPI());
    }

    public static Movie[] getMoviesFromAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(MOVIES_API))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), Movie[].class);
    }

    public static Actor[] getActorsFromAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ACTORS_API))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), Actor[].class);
    }

    public static User[] getUsersFromAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USERS_API))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), User[].class);

    }
    public static Comment[] getCommentsFromAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(COMMENTS_API))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), Comment[].class);
    }

    public String getToken(User user) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date exp = c.getTime();

        SecretKey key = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        return Jwts.builder()
                .signWith(key)
                .setHeaderParam("typ", "JWT")
                .setIssuer("iemdb.ir")
                .setIssuedAt(new Date())
                .setExpiration(exp)
                .claim("user", user.getEmail())
                .compact();
    }

}
