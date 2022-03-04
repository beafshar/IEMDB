import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import java.time.LocalDate;
import Error.*;

import static java.lang.Integer.valueOf;

public class IEMDBController {
    private static final String SERVICE_API = "http://138.197.181.131:5000";
    private static final String MOVIES_API = SERVICE_API + "/api/movies";
    private static final String ACTORS_API = SERVICE_API + "/api/actors";
    private static final String USERS_API = SERVICE_API + "/api/users";
    private static final String COMMENTS_API = SERVICE_API + "/api/comments";

    static final ActorHandler actorHandler = new ActorHandler();
    private static final UserHandler userHandler = new UserHandler();
    private static final MovieHandler movieHandler = new MovieHandler();
    private static final CommentHandler commentHandler = new CommentHandler();


    private static void handleRequest() {
        Javalin app = Javalin.create().start(8081);
        app.get("/", ctx -> ctx.result("Welcome to IEMDB!"));
        app.get("/movies", context -> {
            Document template = getMovies();
            context.html(template.html());
        });
        app.get("/movies/{movie_id}", context -> {
            Document template = getMovie(context.pathParam("movie_id"));
            context.html(template.html());
        });
        app.get("/actors/{actor_id}", context -> {
            Document template = getActor(context.pathParam("actor_id"));
            context.html(template.html());
        });
        app.get("/watchList/{user_id}", context -> {
            Document template = getWatchList(context.pathParam("user_id"));
            context.html(template.html());
        });
        app.get("/watchList/{user_id}/{movie_id}", context -> {
            Document template = addToWatchList(context.pathParam("user_id"),context.pathParam("movie_id"));
            context.html(template.html());
        });
        app.post("/removeFromWatchList/{user_id}/{movie_id}", context -> {
            Document template = removeFromWatchList(context.pathParam("user_id"),context.pathParam("movie_id"));
            context.html(template.html());
            context.redirect("/watchList/" + context.pathParam("user_id"));
        });
        app.get("/rateMovie/{user_id}/{movie_id}/{rate}", context -> {
            Document template = rateMovie(context.pathParam("user_id"),context.pathParam("movie_id"),context.pathParam("rate"));
            context.html(template.html());
        });
        app.get("/voteComment/{user_id}/{comment_id}/{vote}", context -> {
            Document template = voteComment(context.pathParam("user_id"),context.pathParam("comment_id"),context.pathParam("vote"));
            context.html(template.html());
        });
        app.get("/movies/search/{genre}", context -> {
            Document template = getMovieByGenre(context.pathParam("genre"));
            context.html(template.html());
        });
        app.get("/movies/search/{start_year}/{end_year}", context -> {
            Document template = getMovieByYear(context.pathParam("start_year"), context.pathParam("end_year"));
            context.html(template.html());
        });
    }

    private static Element showMovies(Movie movie){
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
        for (String g : movie.getGenres()) {
            genres += g + ", ";
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
        row.append("<td><a href=\"/movies/" + new DecimalFormat("00").format(movie.getId()) + "\">Link</a></td>");
        return row;
    }

    private static Document getMovies() throws IOException {
        Document template = Jsoup.parse(new File("src/main/template/movies.html"), "utf-8");
        Element table = template.selectFirst("tbody");
        for (Movie movie : movieHandler.movies.values())
            table.append(showMovies(movie).html());
        return template;
    }

    private static Document getMovie(String movie_id) throws IOException {
        try {
            Document template = Jsoup.parse(new File("src/main/template/movie.html"), "utf-8");
            Movie movie = movieHandler.findMovie(valueOf(movie_id));
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
                String like = "<td>"
                        +          "<form action=\"\" method=\"POST\">"
                        +            "<label for=\"\">";
                like += comment.getLikes();
                like += "</label>"
                        +            "<input"
                        +              "id=\"form_comment_id\""
                        +              "type=\"hidden\""
                        +              "name=\"comment_id\"";
                like += " value=\"" + comment.getId() + "\"";
                like += "/>"
                        +            "<button type=\"submit\">like</button>"
                        +          "</form>"
                        +        "</td>";
                tr.append(like);
                String dislike = "<td>"
                        +          "<form action=\"\" method=\"POST\">"
                        +            "<label for=\"\">";
                dislike += comment.getDislikes();
                dislike += "</label>"
                        +            "<input"
                        +              "id=\"form_comment_id\""
                        +              "type=\"hidden\""
                        +              "name=\"comment_id\"";
                dislike += " value=\"" + comment.getId() + "\"";
                dislike += "/>"
                        +            "<button type=\"submit\">dislike</button>"
                        +          "</form>"
                        +        "</td>";
                tr.append(dislike);
                table.append(tr.html());
            }
            return template;
        }
        catch (MovieNotFound exp) {
            Document template = Jsoup.parse(new File("src/main/template/404.html"), "utf-8");
            return template;
        }
    }

    private static Document getActor(String actor_id) throws IOException {
        try {
            Document template = Jsoup.parse(new File("src/main/template/actor.html"), "utf-8");
            Actor actor = actorHandler.findActor(valueOf(actor_id));
            template.selectFirst("#name").html(actor.getName());
            template.selectFirst("#birthDate").html(actor.getBirthDate());
            template.selectFirst("#nationality").html(actor.getNationality());
            template.selectFirst("#tma").html(Integer.toString(actor.getMovies().size()));
            Element table = template.selectFirst("tbody");
            for (Movie movie : actor.getMovies()) {
                Element tr = new Element("tr");
                tr.append("<td> " + movie.getName() + "</td>");
                tr.append("<td> " + movie.getImdbRate() + "</td>");
                tr.append("<td> " + movie.getRating() + "</td>");
                tr.append("<td><a href=\"/movies/" + new DecimalFormat("00").format(movie.getId()) +"\">Link</a></td>");
                table.append(tr.html());
            }
            return template;
        }
        catch (ActorNotFound exp) {
            Document template = Jsoup.parse(new File("src/main/template/404.html"), "utf-8");
            return template;
        }
    }

    private static Document voteComment (String user_id, String comment_id, String vote) throws IOException {
        try{
            User user = userHandler.findUser(user_id);
            Comment comment = commentHandler.findComment(valueOf(comment_id));
            comment.addVote(user_id, valueOf(vote));
            Document template = Jsoup.parse(new File("src/main/template/200.html"), "utf-8");
            return template;
        }
        catch (UserNotFound|CommentNotFound exp) {
            Document template = Jsoup.parse(new File("src/main/template/404.html"), "utf-8");
            return template;
        }
        catch (InvalidVoteValue exp) {
            Document template = Jsoup.parse(new File("src/main/template/403.html"), "utf-8");
            return template;
        }
    }
    private static Document rateMovie(String user_id, String movie_id, String rate) throws IOException {
        try{
            User user = userHandler.findUser(user_id);
            Movie movie = movieHandler.findMovie(valueOf(movie_id));
            movie.rateMovie(user_id, valueOf(rate));
            Document template = Jsoup.parse(new File("src/main/template/200.html"), "utf-8");
            return template;
        }
        catch (UserNotFound|MovieNotFound exp) {
            Document template = Jsoup.parse(new File("src/main/template/404.html"), "utf-8");
            return template;
        }
        catch (InvalidRateScore exp) {
            Document template = Jsoup.parse(new File("src/main/template/403.html"), "utf-8");
            return template;
        }
    }

    private static Document removeFromWatchList(String user_id, String movie_id) throws IOException {
        try {
            User user = userHandler.findUser(user_id);
            Movie movie = movieHandler.findMovie(valueOf(movie_id));
            user.removeFromWatchList(valueOf(movie_id));
            Document template = Jsoup.parse(new File("src/main/template/200.html"), "utf-8");
            return template;
        }
        catch (UserNotFound|MovieNotFound exp) {
            Document template = Jsoup.parse(new File("src/main/template/404.html"), "utf-8");
            return template;
        }
    }
    private static Document addToWatchList(String user_id, String movie_id) throws IOException {
        try {
            User user = userHandler.findUser(user_id);
            Movie movie = movieHandler.findMovie(valueOf(movie_id));
            user.addToWatchList(valueOf(movie_id));
            Document template = Jsoup.parse(new File("src/main/template/200.html"), "utf-8");
            return template;
        }
        catch (UserNotFound|MovieNotFound exp) {
            Document template = Jsoup.parse(new File("src/main/template/404.html"), "utf-8");
            return template;
        }
        catch (MovieAlreadyExists|AgeLimitError exp){
            Document template = Jsoup.parse(new File("src/main/template/403.html"), "utf-8");
            return template;
        }
    }
    private static Document getWatchList(String user_id) throws IOException {
        try {
            Document template = Jsoup.parse(new File("src/main/template/watchlist.html"), "utf-8");
            User user = userHandler.findUser(user_id);
            template.selectFirst("#name").html(user.getName());
            template.selectFirst("#nickname").html(user.getNickname());
            Element table = template.selectFirst("tbody");
            for (Integer id : user.getWatch()) {
                Movie movie = movieHandler.movies.get(id);
                Element row = new Element("tr");
                row.append("<td>" + movie.getName() + "</td>");
                row.append("<td>" + movie.getReleaseDate() + "</td>");
                row.append("<td>" + movie.getDirector() + "</td>");
                String genres = "<td>";
                for (String genre : movie.getGenres()) {
                    genres += genre + ", ";
                }
                genres += "</td>";
                row.append(genres);
                row.append("<td>" + movie.getImdbRate() + "</td>");
                row.append("<td>" + movie.getRating() + "</td>");
                row.append("<td>" + movie.getDuration() + "</td>");
                row.append("<td><a href=\"/movies/" + new DecimalFormat("00").format(movie.getId()) + "\">Link</a></td>");
                String remove = "<td>"
                        + "<form action= \"/removeFromWatchList/" + user_id + "/"
                        + new DecimalFormat("00").format(movie.getId())
                        +"\" method=\"POST\" >"
                        + "<input id=\"form_movie_id\" type=\"hidden\" name=\"movie_id\" value=\"";
                remove += movie.getId();
                remove += "\">"
                        + "<button type=\"submit\">Remove</button>"
                        + "</form>"
                        + "</td>";
                row.append(remove);
                table.append(row.html());
            }
            return template;
        }
        catch (UserNotFound exp) {
            Document template = Jsoup.parse(new File("src/main/template/404.html"), "utf-8");
            return template;
        }
    }

    private static Document getMovieByGenre(String movie_genre) throws IOException {
        Document template = Jsoup.parse(new File("src/main/template/movies.html"), "utf-8");
        Element table = template.selectFirst("tbody");
        for (Movie movie : movieHandler.movies.values())
            for (String genre : movie.getGenres())
                if (genre.equals(movie_genre))
                    table.append(showMovies(movie).html());
        return template;
    }

    private static Document getMovieByYear(String start_year, String end_year) throws IOException {
        Document template = Jsoup.parse(new File("src/main/template/movies.html"), "utf-8");
        Element table = template.selectFirst("tbody");
        for (Movie movie : movieHandler.movies.values()) {
            LocalDate movie_year = LocalDate.parse(movie.getReleaseDate());
            if (movie_year.getYear() >= Integer.parseInt(start_year) && movie_year.getYear() <= Integer.parseInt(end_year))
                table.append(showMovies(movie).html());
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
