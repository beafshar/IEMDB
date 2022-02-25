import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import Error.*;
import java.util.HashSet;
import java.util.Set;

public class MovieHandler {
    public static Set<Movie> movies = new HashSet<Movie>();

    public JSONObject addMovie(String jsonData) {
        addMovie AM = new addMovie();
        return AM.execute(jsonData);
    }

    public static JSONObject getMovieList(String jsonData) throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        for(Movie movie : movies) {
                    JSONObject jo1 = new JSONObject();
                    jo1.put("movieId", movie.getId());
                    jo1.put("name", movie.getName());
                    jo1.put("director", movie.getDirector());
                    JSONArray jsonArray = new JSONArray();
                    for(String g : movie.getGenres()) {
                        jsonArray.put(g);
                    }
                    jo1.put("genres", jsonArray);
                    jo1.put("rating", movie.getRating());
                    ja.put(jo1);
        }
        jo.put("MovieListByGenre", ja);
        String data = jo.toString();
        JSONObject response = new JSONObject();
        response.put("success", true);
        response.put("data", data);
        return response;
    }

    public static JSONObject getMovieById(String jsonData) throws JSONException {
        JSONObject response = new JSONObject();
        JSONObject json = new JSONObject(jsonData);
        int movie_id = json.getInt("movieId");
        for(Movie movie: movies) {
            if(movie.getId() == movie_id) {
                JSONObject jo = new JSONObject();
                jo.put("movieId", movie.getId());
                jo.put("name", movie.getName());
                jo.put("summary", movie.getSummary());
                jo.put("releaseDate", movie.getReleaseDate());
                jo.put("director", movie.getDirector());
                JSONArray w = new JSONArray();
                for(String writer: movie.getWriters()) {
                    w.put(writer);
                }
                jo.put("writers", w);
                JSONArray g = new JSONArray();
                for(String genre: movie.getGenres()) {
                    g.put(genre);
                }
                jo.put("genres", g);
                JSONArray c = new JSONArray();
                for(int cast : movie.getCast()) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("actorId", cast);
                    for(Actor actor : ActorHandler.actors)
                        if(actor.getId() == cast)
                            jsonObject.put("name", actor.getName());
                    c.put(jsonObject);
                }
                jo.put("cast", c);
                jo.put("rating", movie.getRating());
                jo.put("duration", movie.getDuration());
                jo.put("ageLimit", movie.getAgeLimit());
                JSONArray comm = new JSONArray();
                for(Comment comment : movie.getComments()) {
                    JSONObject cc = new JSONObject();
                    cc.put("commentId", comment.getId());
                    cc.put("userEmail", comment.getUserEmail());
                    cc.put("text", comment.getText());
                    cc.put("like", comment.getLikes());
                    cc.put("dislike", comment.getDislikes());
                    comm.put(cc);
                }
                jo.put("comments", comm);
                String data = jo.toString();
                response.put("success", true);
                response.put("data", data);
                return response;
            }
        }
        MovieNotFound err = new MovieNotFound();
        response.put("success", false);
        response.put("data", err.message());
        return response;
    }

    public static JSONObject getMovieByGenre(String jsonData) throws JSONException {
        JSONObject json = new JSONObject(jsonData);
        String movie_genre = json.getString("genre");
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        for(Movie movie : movies) {
            for(String genre : movie.getGenres()) {
                if(genre.equals(movie_genre)) {
                    JSONObject jo1 = new JSONObject();
                    jo1.put("movieId", movie.getId());
                    jo1.put("name", movie.getName());
                    jo1.put("director", movie.getDirector());
                    JSONArray jsonArray = new JSONArray();
                    for(String g : movie.getGenres()) {
                        jsonArray.put(g);
                    }
                    jo1.put("genres", jsonArray);
                    jo1.put("rating", movie.getRating());
                    ja.put(jo1);
                }
            }
        }
        jo.put("MovieListByGenre", ja);
        String data = jo.toString();
        JSONObject response = new JSONObject();
        response.put("success", true);
        response.put("data", data);
        return response;
    }

    public static JSONObject rateMovie(String jsonData) throws JSONException {
        JSONObject response = new JSONObject();
        JSONObject json = new JSONObject(jsonData);
        int movie_id = json.getInt("movieId");
        int score = json.getInt("score");
        String userEmail = json.getString("userEmail");
        for(User user: UserHandler.users) {
            if(user.getEmail().equals(userEmail)) {
                for(Movie movie: movies) {
                    if(movie.getId() == movie_id) {
                        if(score <= 10 && score >= 1) {
                            return movie.rateMovie(user.getEmail(), score);
                        }
                        InvalidRateScore err = new InvalidRateScore();
                        response.put("success", false);
                        response.put("data", err.message());
                        return response;
                    }
                }
                MovieNotFound err = new MovieNotFound();
                response.put("success", false);
                response.put("data", err.message());
                return response;
            }
        }
        UserNotFound err = new UserNotFound();
        response.put("success", false);
        response.put("data", err.message());
        return response;
    }

}
