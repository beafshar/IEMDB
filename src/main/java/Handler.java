import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import org.json.*;

public class Handler {

    private static ActorHandler actorHandler = new ActorHandler();
    private static UserHandler userHandler = new UserHandler();
    private static MovieHandler movieHandler = new MovieHandler();
    public static Integer comment_id = 1;

    public static void main(String[] args) throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null)
        {
            String[] arrOfStr = line.split(" ", 2);
            String response = "";

            switch(arrOfStr[0]) {

                case "addActor":
                    response = actorHandler.addActor(arrOfStr[1]);
                    break;
                case "addMovie":
                    response = movieHandler.addMovie(arrOfStr[1]);
                    break;
                case "addUser":
                    response = userHandler.addUser(arrOfStr[1]);
                    break;
                case "addComment":
                    response = MovieHandler.addComment(arrOfStr[1]);
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
