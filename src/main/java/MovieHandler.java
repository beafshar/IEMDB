import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONException;
import org.json.JSONObject;
import Error.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MovieHandler {
    public static Map<Integer, Movie> movies = new HashMap<>();

    public void setMovies(Movie[] movies) {
        for (Movie movie : movies)
            this.movies.put(movie.getId(), movie);
    }

    public ObjectNode addMovie(String jsonData) throws JSONException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
        ObjectMapper om = new ObjectMapper();
        ObjectNode response = om.createObjectNode();

        JSONObject json = new JSONObject(jsonData);
        movies.remove(json.getInt("id"));
        Movie movie = objectMapper.readValue(jsonData, Movie.class);
        if (ActorHandler.actors.keySet().containsAll(movie.getCast())) {
            MovieHandler.movies.put(json.getInt("id"), movie);
            response.put("success", true);
            response.put("data", "movie added successfully");
            return response;
        }

        return ActorHandler.ActorNotFound();
    }


    public static Movie returnMovieObjectGivenId(int id){
        return movies.get(id);
    }

    public static ObjectNode MovieNotFound(){
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        MovieNotFound err = new MovieNotFound();
        response.put("success", false);
        response.put("error", err.getMessage());
        return response;
    }
    public static ObjectNode getMovieList() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        ObjectNode jo = objectMapper.createObjectNode();
        ArrayNode ja = objectMapper.createArrayNode();
        for(Movie movie : movies.values()) {
                    ObjectNode jo1 = objectMapper.createObjectNode();
                    jo1.put("movieId", movie.getId());
                    jo1.put("name", movie.getName());
                    jo1.put("director", movie.getDirector());
                    ArrayNode jsonArray = objectMapper.createArrayNode();
                    for(String g : movie.getGenres()) {
                        jsonArray.add(g);
                    }
                    jo1.putArray("genres").addAll(jsonArray);
                    jo1.put("rating", movie.getRating());
                    ja.add(jo1);
        }
        jo.putArray("MovieListByGenre").addAll(ja);
        response.put("success", true);
        response.set("data", jo);
        return response;
    }

    public static ObjectNode getMovieById(String jsonData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        JSONObject json = new JSONObject(jsonData);
        int movie_id = json.getInt("movieId");
        Movie movie = movies.get(movie_id);
        if(movie != null) {
            ObjectNode jo = objectMapper.createObjectNode();
            jo.put("movieId", movie.getId());
            jo.put("name", movie.getName());
            jo.put("summary", movie.getSummary());
            jo.put("releaseDate", movie.getReleaseDate());
            jo.put("director", movie.getDirector());
            ArrayNode w = objectMapper.createArrayNode();
            for (String writer : movie.getWriters()) {
                w.add(writer);
            }
            jo.putArray("writers").addAll(w);
            ArrayNode g = objectMapper.createArrayNode();
            for (String genre : movie.getGenres()) {
                g.add(genre);
            }
            jo.putArray("genres").addAll(g);
            ArrayNode c = objectMapper.createArrayNode();
            for (int cast : movie.getCast()) {
                ObjectNode jsonObject = objectMapper.createObjectNode();
                jsonObject.put("actorId", cast);
                jsonObject.put("name", ActorHandler.findActor(cast).getName());
                c.add(jsonObject);
            }
            jo.putArray("cast").addAll(c);
            jo.put("rating", movie.getRating());
            jo.put("duration", movie.getDuration());
            jo.put("ageLimit", movie.getAgeLimit());
            ArrayNode comm = objectMapper.createArrayNode();
            for (Comment comment : movie.getComments()) {
                ObjectNode cc = objectMapper.createObjectNode();
                cc.put("commentId", comment.getId());
                cc.put("userEmail", comment.getUserEmail());
                cc.put("text", comment.getText());
                cc.put("like", comment.getLikes());
                cc.put("dislike", comment.getDislikes());
                comm.add(cc);
            }
            jo.putArray("comments").addAll(comm);
            response.put("success", true);
            response.set("data", jo);
            return response;
        }
        return MovieNotFound();
    }

    public static ObjectNode getMovieByGenre(String jsonData) throws Exception {
        JSONObject json = new JSONObject(jsonData);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        String movie_genre = json.getString("genre");
        ObjectNode jo = objectMapper.createObjectNode();
        ArrayNode ja = objectMapper.createArrayNode();
        for (Movie movie : movies.values()) {
            for (String genre : movie.getGenres()) {
                if (genre.equals(movie_genre)) {
                    ObjectNode jo1 = objectMapper.createObjectNode();
                    jo1.put("movieId", movie.getId());
                    jo1.put("name", movie.getName());
                    jo1.put("director", movie.getDirector());
                    ArrayNode jsonArray = objectMapper.createArrayNode();
                    for (String g : movie.getGenres()) {
                        jsonArray.add(g);
                    }
                    jo1.putArray("genres").addAll(jsonArray);
                    jo1.put("rating", movie.getRating());
                    ja.add(jo1);
                }
            }
        }
        jo.putArray("MovieListByGenre").addAll(ja);
        response.put("success", true);
        response.set("data", jo);
        return response;
    }

    public static ObjectNode rateMovie(String jsonData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        JSONObject json = new JSONObject(jsonData);

        int movie_id = json.getInt("movieId");
        int score = json.getInt("score");
        String userEmail = json.getString("userEmail");
        User user = UserHandler.findUser(userEmail);
        if(user != null) {
            Movie movie = MovieHandler.returnMovieObjectGivenId(movie_id);
            if(movie != null) {
                if (score <= 10 && score >= 1) {
                    return movie.rateMovie(user.getEmail(), score);
                }
                InvalidRateScore err = new InvalidRateScore();
                response.put("success", false);
                response.put("data", err.getMessage());
                return response;
            }
            return MovieHandler.MovieNotFound();
        }
        return UserHandler.UserNotFound();
    }

}
