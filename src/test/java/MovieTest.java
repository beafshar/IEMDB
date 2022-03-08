import Model.IEMDBController;
import Model.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import static org.junit.Assert.assertEquals;

public class MovieTest {
    IEMDBController imdb = new IEMDBController();

    @Before
    public void setup() throws Exception {
        imdb.setDatasets();
    }

    @After
    public void teardown() {
        imdb = null;
    }

//    @Test
//    public void userSuccessfullyRatesMovie() throws Exception {
//        String score = "4";
//        Movie movie =  imdb.movieHandler.findMovie(3);
//        double rate = movie.getRating();
//        double ratingCount = movie.getRatingCount();
//        imdb.rateMovie("sara@ut.ac.ir", "03", "4");
//        assertEquals((rate+Double.parseDouble(score)/(ratingCount+1)), movie.getRating(), 0.01);
//    }
//
//    @Test
//    public void userRatesAMovieTwice() throws Exception {
//        String score = "5";
//        Movie movie =  imdb.movieHandler.findMovie(3);
//        double rate = movie.getRating();
//        double ratingCount = movie.getRatingCount();
//        imdb.rateMovie("sara@ut.ac.ir", "03", "4");
//        imdb.rateMovie("sara@ut.ac.ir", "03", "5");
//        assertEquals((rate+Double.parseDouble(score)/(ratingCount+1)), movie.getRating(), 0.01);
//    }
//
//    @Test
//    public void userNotFound() throws Exception {
//        Document doc = Jsoup.parse(new File("src/main/template/404.html"), "utf-8");
//        assertEquals(doc.toString(), imdb.rateMovie("sara@ut.ir", "03", "4").toString());
//    }
//
//    @Test
//    public void movieNotFound() throws Exception {
//        Document doc = Jsoup.parse(new File("src/main/template/404.html"), "utf-8");
//        assertEquals(doc.toString(), imdb.rateMovie("sara@ut.ac.ir", "50", "4").toString());
//    }
//
//    @Test
//    public void getMovieByYear() throws Exception {
//        int startYear = 2000;
//        int endYear = 2010;
//        assertEquals(6, imdb.movieHandler.getMovieByYear(startYear, endYear).size());
//    }
}
