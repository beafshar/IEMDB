import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import Error.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String birthDate;
    private Set<Movie> WatchList = new HashSet<Movie>();

    public User()
    {

    }

    public User(String _email, String _password, String _nickname, String _name, String _birthDate)
    {
        email = _email;
        password = _password;
        name = _name;
        nickname = _nickname;
        birthDate = _birthDate;
    }

    public String getEmail()
    {
        return email;
    }
    public String getName()
    {
        return name;
    }
    public String getBirthDate() {return birthDate;}

    private int calculateUserAge() {
        LocalDate birth = LocalDate.parse(this.birthDate);
        LocalDate curDate = LocalDate.now();
        return curDate.getYear() - birth.getYear();
    }

    public JSONObject addToWatchList(int movieId) throws JSONException {
        JSONObject response = new JSONObject();
        for(Movie movie: MovieHandler.movies) {
            if(movie.getId() == movieId) {
                if(calculateUserAge() >= movie.getAgeLimit()) {
                    if(WatchList.contains(movie)) {
                        MovieAlreadyExists err = new MovieAlreadyExists();
                        response.put("success", false);
                        response.put("error", err.message());
                        return response;
                    }
                    WatchList.add(movie);
                    response.put("success", true);
                    response.put("data", "movie added to watchlist successfully");
                    return response;
                }
                AgeLimitError err = new AgeLimitError();
                response.put("success", false);
                response.put("error", err.message());
                return response;
            }
        }
        MovieNotFound err = new MovieNotFound();
        response.put("success", false);
        response.put("error", err.message());
        return response;
    }

    public JSONObject removeFromWatchlist(int movieId) throws JSONException {
        for(Movie movie : WatchList) {
            if(movie.getId() == movieId) {
                WatchList.remove(movie);
                JSONObject response = new JSONObject();
                response.put("success", true);
                response.put("data", "movie removed from watchlist successfully");
                return response;
            }
        }

        return null;
    }

    public JSONObject getWatchList() throws JSONException {
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        for(Movie movie : WatchList) {
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
        jo.put("WatchList", ja);
        String data = jo.toString();
        JSONObject response = new JSONObject();
        response.put("success", true);
        response.put("data", data);
        return response;
    }
}
