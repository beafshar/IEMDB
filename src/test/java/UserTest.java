import org.json.JSONArray;
import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class UserTest {
    User user;
    UserHandler UH;
    Set<User> users= new HashSet<User>();
    Movie movie1;
    Movie movie2;


    @Before
    public void setup() throws JSONException {
        UH = new UserHandler();
        user = new User("Mahdi@gmail.com","1234", "mahdi", "mahdi mahdavi","2000-02-01");
        users.add(user);
    }

    @After
    public void tearDown()
    {
        UH = null;
        users = null;
        user = null;
        movie1 = null;
        movie2 = null;
    }

    private JSONArray create_genre(String genre)
    {
        JSONArray ja = new JSONArray();
        ja.put(genre);
        return ja;
    }

    @Test
    public void userAddsMovieToWatchListSuccesfully()
    {

    }

    @Test
    public void userAgeCrossesMovieAgeLimit()
    {

    }


    @Test
    public void movieNotFound()
    {

    }
}
