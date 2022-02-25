import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Handler {

    public static Set<Actor> actors = new HashSet<Actor>();
    public static Set<User> Users= new HashSet<User>();
    public static Set<Movie> Movies = new HashSet<Movie>();

    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null)
        {
            String[] arrOfStr = line.split(" ", 2);
            String response = "";
            switch(arrOfStr[0]) {

                case "addActor":
                    addActor AA = new addActor();
                    response = AA.execute(arrOfStr[1]);
                    break;
                case "addMovie":
                    addMovie AM = new addMovie();
                    response = AM.execute(arrOfStr[1]);
                    break;
                case "addUser":
                    addUser AU = new addUser();
                    response = AU.execute(arrOfStr[1]);
                    break;
                case "addComment":
                    System.out.println(arrOfStr[1]);
                    break;
                case "rateMovie":
                    System.out.println(arrOfStr[1]);
                    break;
                case "voteComment":
                    System.out.println(arrOfStr[1]);
                    break;
                case "addToWatchList":
                    System.out.println(arrOfStr[1]);
                    break;
                case "removeFromWatchList":
                    System.out.println(arrOfStr[1]);
                    break;
                case "getMovieList":
                    System.out.println(arrOfStr[0]);
                    break;
                case "getMovieById":
                    System.out.println(arrOfStr[1]);
                    break;
                case "getMoviesByGenre":
                    System.out.println(arrOfStr[1]);
                    break;
                case "getWatchList":
                    System.out.println(arrOfStr[1]);
                    break;
                default:
                    System.out.println("Error.InvalidCommand");
                    break;
            }
            System.out.println(response);
        }
    }
}
