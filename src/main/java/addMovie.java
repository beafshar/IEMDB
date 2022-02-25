import Command.Command;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import Error.MovieAlreadyExists;
import org.json.JSONObject;

public class addMovie implements Command {
    @Override
    public String execute(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            JSONObject json = new JSONObject(jsonData);
            Movie movie = new Movie(json.getInt("id"), json.getString("name"), json.getString("summary"), json.getString("releaseDate"), json.getString("director"), json.getJSONArray("writers"), json.getJSONArray("genres"),json.getJSONArray("cast"), json.getDouble("imdbRate"), json.getInt("duration"), json.getInt("ageLimit"));
            System.out.println(movie.getCast());

            //check cast existance


            if (MovieHandler.movies.contains(movie))
            {
                MovieAlreadyExists err = new MovieAlreadyExists();
                return "{\"success\":false \"data\": " + err.message()+ "\"}";
            }

            MovieHandler.movies.add(movie);

            return "{\"success\":true \"data\": \"movie added successfully\"}";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
