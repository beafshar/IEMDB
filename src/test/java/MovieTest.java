import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MovieTest {
    User user;
    MovieHandler MV;

    @Before
    public void setup() throws JSONException {
        user = new User("Mahdi@gmail.com","1234", "mahdi", "mahdi mahdavi","2000-02-01");
//        users = new UserHandler();
//        users.addUser(user);
        MV = new MovieHandler();
        MV.addMovie("{\"id\": 3, \"name\": \"Seven\", \"summary\": \"Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his motives.\", \"releaseDate\":\"1995-09-22\", \"director\": \"David Fincher\", \"writers\":[\"Andrew Kevin Walker\"], \"genres\": [\"Drama\", \"Crime\", \"Mystery\"], \"cast\": [4, 6], \"imdbRate\": 8.6, \"duration\": \"127\", \"ageLimit\": 16}");

    }

    @After
    public void teardown()
    {
        user = null;
        MV = null;
    }

    @Test
    public void userSuccesfullyRatesMovie() throws JSONException
    {
        int score = 4;
        Movie movie =  MV.returnMovieObjectGivenId(3);
        double rate  = movie.getRating();
        double ratingCount = movie.getRatingCount();
        movie.rateMovie(user.getEmail(), score);
        assertEquals(ratingCount + 1, movie.getRatingCount());
        assertEquals((rate+score)/ratingCount, movie.getRating());

    }

    @Test
    public void userRatesAMovieTwice()
    {

    }

    @Test
    public void userNotFound()
    {

    }

    @Test
    public void invalidMovieScore()
    {

    }

    @Test
    public void movieNotFound()
    {

    }

    @Test
    public void getMoviesByGenresSuccessfully()
    {

    }

    @Test
    public void noMovieFoundForGivenGenre()
    {

    }
}
