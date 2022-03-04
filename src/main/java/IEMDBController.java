import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class IEMDBController {
    private static final String SERVICE_API = "http://138.197.181.131:5000";
    private static final String MOVIES_API = SERVICE_API + "/api/movies";
    private static final String ACTORS_API = SERVICE_API + "/api/actors";
    private static final String USERS_API = SERVICE_API + "/api/users";
    private static final String COMMENTS_API = SERVICE_API + "/api/comments";

    private static final ActorHandler actorHandler = new ActorHandler();
    private static final UserHandler userHandler = new UserHandler();
    private static final MovieHandler movieHandler = new MovieHandler();
    private static final CommentHandler commentHandler = new CommentHandler();


    public static void main(String[] args) throws IOException, InterruptedException {
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

        System.out.println(response.body());

        ObjectMapper objectMapper = new ObjectMapper();
        Movie[] movies = objectMapper.readValue(response.body(), Movie[].class);
        System.out.println(movies.length);
        return movies;

    }
    public static Actor[] getActorsFromAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ACTORS_API))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());

        ObjectMapper objectMapper = new ObjectMapper();
        Actor[] actors = objectMapper.readValue(response.body(), Actor[].class);
        System.out.println(actors);
        return actors;

    }
    public static User[] getUsersFromAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(USERS_API))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());

        ObjectMapper objectMapper = new ObjectMapper();
        User[] users = objectMapper.readValue(response.body(), User[].class);
        System.out.println(users.length);
        return users;

    }
    public static Comment[] getCommentsFromAPI() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(COMMENTS_API))
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());

        ObjectMapper objectMapper = new ObjectMapper();
        Comment[] comments = objectMapper.readValue(response.body(), Comment[].class);
        System.out.println(comments.length);
        return comments;

    }


}
