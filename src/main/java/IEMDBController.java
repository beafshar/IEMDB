import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.File;
import java.text.DecimalFormat;

import static java.lang.Integer.valueOf;

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


    private static void handleRequest() {
        Javalin app = Javalin.create().start(8081);
        app.get("/", ctx -> ctx.result("Hello World!"));
        app.get("/movies", context -> {
            Document template = getMovies();
            context.html(template.html());
        });
        app.get("/movies/{movie_id}", context -> {
            Document template = getMovie(context.pathParam("movie_id"));
            context.html(template.html());
        });
    }


    private static Document getMovie(String movie_id) throws IOException {
        Document template = Jsoup.parse(new File("src/main/template/movie.html"), "utf-8");
        Movie movie = movieHandler.movies.get(valueOf(movie_id));
        template.selectFirst("#name").html(movie.getName());
        template.selectFirst("#summary").html(movie.getSummary());
        template.selectFirst("#releaseDate").html(movie.getReleaseDate());
        template.selectFirst("#director").html(movie.getDirector());
        String writers = "";
        for (String writer : movie.getWriters()) {
            writers += writer + ", ";
        }
        template.selectFirst("#writers").html(writers);
        String genres = "";
        for (String genre : movie.getGenres()) {
            genres += genre + ", ";
        }
        template.selectFirst("#genres").html(genres);
        String casts = "";
        for (Integer cast : movie.getCast()) {
            casts += actorHandler.actors.get(cast).getName() + ", ";
        }
        template.selectFirst("#cast").html(casts);
        template.selectFirst("#imdbRate").html(Double.toString(movie.getImdbRate()));
        template.selectFirst("#rating").html(Double.toString(movie.getRating()));
        template.selectFirst("#duration").html(Long.toString(movie.getDuration()));
        template.selectFirst("#ageLimit").html(Integer.toString(movie.getAgeLimit()));
        Element table = template.selectFirst("tbody");
        for (Comment comment : movie.getComments()) {
            Element tr = new Element("tr");
            tr.append("<td> " + userHandler.users.get(comment.getUserEmail()).getNickname() + "</td>");
            tr.append("<td> " + comment.getText() + "</td>");
            String like = "<td>\n" +
                    "          <form action=\"\" method=\"POST\">\n" +
                    "            <label for=\"\">";
            like += comment.getLikes();
            like += "</label>\n" +
                    "            <input\n" +
                    "              id=\"form_comment_id\"\n" +
                    "              type=\"hidden\"\n" +
                    "              name=\"comment_id\"";
            like += " value=\"" + comment.getId() + "\"";
            like += "/>\n" +
                    "            <button type=\"submit\">like</button>\n" +
                    "          </form>\n" +
                    "        </td>";
            tr.append(like);
            String dislike = "<td>\n" +
                    "          <form action=\"\" method=\"POST\">\n" +
                    "            <label for=\"\">";
            dislike += comment.getDislikes();
            dislike += "</label>\n" +
                    "            <input\n" +
                    "              id=\"form_comment_id\"\n" +
                    "              type=\"hidden\"\n" +
                    "              name=\"comment_id\"";
            dislike += " value=\"" + comment.getId() + "\"";
            dislike += "/>\n" +
                    "            <button type=\"submit\">dislike</button>\n" +
                    "          </form>\n" +
                    "        </td>";
            tr.append(dislike);
            table.append(tr.html());
        }
        return template;
    }

    private static Document getMovies() throws IOException {
        Document template = Jsoup.parse(new File("src/main/template/movies.html"), "utf-8");
        Element table = template.selectFirst("tbody");
        for (Movie movie : movieHandler.movies.values()) {
            Element row = new Element("tr");
            row.append("<td>" + movie.getName() + "</td>");
            row.append("<td>" + movie.getSummary() + "</td>");
            row.append("<td>" + movie.getReleaseDate() + "</td>");
            row.append("<td>" + movie.getDirector() + "</td>");
            String writers = "<td>";
            for (String writer : movie.getWriters()) {
                writers += writer + ", ";
            }
            writers += "</td>";
            row.append(writers);
            String genres = "<td>";
            for (String genre : movie.getGenres()) {
                genres += genre + ", ";
            }
            genres += "</td>";
            row.append(genres);
            String casts = "<td>";
            for (Integer cast : movie.getCast()) {
                casts += actorHandler.actors.get(cast).getName() + ", ";
            }
            casts += "</td>";
            row.append(casts);
            row.append("<td>" + movie.getImdbRate() + "</td>");
            row.append("<td>" + movie.getRating() + "</td>");
            row.append("<td>" + movie.getDuration() + "</td>");
            row.append("<td>" + movie.getAgeLimit() + "</td>");
            row.append("<td><a href=\"/movies/" + new DecimalFormat("00").format(movie.getId()) +"\">Link</a></td>");
            table.append(row.html());
        }

        return template;
    }

    public static void setDatasets() throws IOException, InterruptedException {
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

    public static void main(String[] args) throws IOException, InterruptedException {
        setDatasets();
        handleRequest();
    }
}
