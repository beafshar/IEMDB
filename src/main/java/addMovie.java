import Command.Command;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import Error.ActorNotFound;
import org.json.JSONObject;

public class addMovie implements Command {
    @Override
    public JSONObject execute(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            JSONObject json = new JSONObject(jsonData);
            Movie movie = new Movie(json.getInt("id"), json.getString("name"), json.getString("summary"), json.getString("releaseDate"), json.getString("director"), json.getJSONArray("writers"), json.getJSONArray("genres"),json.getJSONArray("cast"), json.getDouble("imdbRate"), json.getInt("duration"), json.getInt("ageLimit"));

            int flag = movie.getCast().size();
            for (int i = 0; i < movie.getCast().size(); i++) {
                for (Actor actor : ActorHandler.actors) {
                    if (movie.getCast().get(i).equals(actor.getId())) {
                        flag--;
                    }
                }
            }
            JSONObject response = new JSONObject();
            if(flag != 0) {
                ActorNotFound err = new ActorNotFound();
                response.put("success", false);
                response.put("data", err.message());
                return response;
            }

            MovieHandler.movies.add(movie);
            response.put("success", true);
            response.put("data", "movie added successfully");
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
