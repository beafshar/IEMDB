import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Error.*;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class UserTest {

    IEMDBController imdb = new IEMDBController();

    @Before
    public void setup() throws IOException, InterruptedException {
        imdb.setDatasets();
    }

    @After
    public void tearDown() {
        imdb = null;
    }

    @Test
    public void getWatchListUserNotFound() throws IOException {
        Document doc = Jsoup.parse(new File("src/main/template/404.html"), "utf-8");
        assertEquals(doc.toString(), imdb.getWatchList("sara@ir").toString());
    }

    @Test
    public void getWatchListEmpty() throws IOException, UserNotFound {
        User user = imdb.userHandler.findUser("sara@ut.ac.ir");
        assertEquals(0, user.getWatch().size());
    }

    @Test
    public void getWatchList() throws IOException, UserNotFound {
        User user = imdb.userHandler.findUser("sara@ut.ac.ir");
        imdb.addToWatchList("sara@ut.ac.ir", "03");
        assertEquals(1, user.getWatch().size());
        assertEquals(Integer.valueOf(3), user.getWatch().get(0));
    }

}
