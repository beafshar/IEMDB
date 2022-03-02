import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class MovieTest {
    User user;
    MovieHandler MV;
    Movie movie1;
    Movie movie2;
    Set<Movie> movies = new HashSet<Movie>();

    private JSONArray create_genre(String genre)
    {
        JSONArray ja = new JSONArray();
        ja.put(genre);
        return ja;
    }

    @Before
    public void setup() throws Exception {
        user = new User("Mahdi@gmail.com","1234", "mahdi", "mahdi mahdavi","2000-02-01");
        MV = new MovieHandler();
//        movie1 = new Movie(3, "Seven", "David Fincher",create_genre("Drama"),8.6, 18);
//        movie2 = new Movie(4, "Life is Beautiful", "David Fincher",create_genre("Comedy"),9, 14);
//        movies.add(movie1);
//        movies.add(movie2);
//        MV.setMovies(movies);
    }

    @After
    public void teardown()
    {
        user = null;
        MV = null;
        movie1 = null;
        movie2 = null;

    }

    @Test
    public void userSuccesfullyRatesMovie() throws Exception
    {
        int score = 4;
        Movie movie =  MV.returnMovieObjectGivenId(3);
        double rate  = movie.getRating();
        double ratingCount = movie.getRatingCount();
        movie.rateMovie(user.getEmail(), score);
        assertEquals(ratingCount + 1, movie.getRatingCount(), 0.01);
        assertEquals((rate+score)/(ratingCount+1), movie.getRating(), 0.01);

    }

    @Test
    public void userRatesAMovieTwice() throws Exception {
        int score = 4;
        Movie movie =  MV.returnMovieObjectGivenId(3);
        double rate  = movie.getRating();
        double ratingCount = movie.getRatingCount();
        movie.rateMovie(user.getEmail(), score);
        score = 5;
        movie.rateMovie(user.getEmail(), score);
        assertEquals(ratingCount + 1, movie.getRatingCount(), 0.01);
        assertEquals((rate+score)/(ratingCount+1), movie.getRating(), 0.01);
    }

    @Test
    public void userNotFound() throws Exception {
        ObjectNode response = MV.rateMovie("{\"userEmail\": \"beafshar@gmail.com\", \"movieId\": 3, \"score\": 100}");
        System.out.println(response);
        String response_expected = "{\"data\":\"UserNotFound\",\"success\":false}";
        assertEquals(response_expected, response.toString());
    }


    @Test
    public void getMoviesByGenresSuccessfully() throws Exception {
        ObjectNode response = MV.getMovieByGenre("{\"genre\": \"Drama\"}");
        String response_expected = "{\"data\":\"{\\\"MovieListByGenre\\\":[{\\\"director\\\":\\\"David Fincher\\\",\\\"genres\\\":[\\\"Drama\\\"],\\\"name\\\":\\\"Seven\\\",\\\"rating\\\":0,\\\"movieId\\\":3}]}\",\"success\":true}";
        assertEquals(response_expected, response.toString());
    }

    @Test
    public void noMovieFoundForGivenGenre() throws Exception {
        ObjectNode response =  MV.getMovieByGenre("{\"genre\": \"Mystery\"}");
        System.out.println(response);
        String response_expected = "{\"data\":\"{\\\"MovieListByGenre\\\":[]}\",\"success\":true}";
        assertEquals(response_expected, response.toString());

    }
}
