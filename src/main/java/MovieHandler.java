import java.util.HashSet;
import java.util.Set;

public class MovieHandler {
    public static Set<Movie> movies = new HashSet<Movie>();

    public String addMovie(String jsonData) {
        addMovie AM = new addMovie();
        return AM.execute(jsonData);
    }

}
