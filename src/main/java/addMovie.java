import Command.Command;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import Error.MovieAlreadyExists;

public class addMovie implements Command {
    @Override
    public String execute(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Movie movie = objectMapper.readValue(jsonData, Movie.class);
            System.out.println(movie.getCast());
            System.out.println(movie.getName());
            System.out.println(movie.getId());

            //check cast existance


            if (Handler.Movies.contains(movie))
            {
                MovieAlreadyExists err = new MovieAlreadyExists();
                return "{\"success\":false \"data\": " + err.message()+ "\"}";
            }

            Handler.Movies.add(movie);

            return "{\"success\":true \"data\": \"movie added successfully\"}";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
