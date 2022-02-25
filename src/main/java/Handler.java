import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.*;

public class Handler {

    private static ActorHandler actorHandler = new ActorHandler();
    private static UserHandler userHandler = new UserHandler();
    private static MovieHandler movieHandler = new MovieHandler();
    private static CommentHandler commentHandler = new CommentHandler();

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
                    response = commentHandler.addComment(arrOfStr[1]);
                    break;
                case "rateMovie":
                    response = movieHandler.rateMovie(arrOfStr[1]);
                    break;
                case "voteComment":
                    response = CommentHandler.voteComment(arrOfStr[1]);
                    break;
                case "addToWatchList":
                    response = UserHandler.addToWatchList(arrOfStr[1]);
                    break;
                case "removeFromWatchList":
                    response = UserHandler.removeFromWatchList(arrOfStr[1]);
                    break;
                case "getMovieList":
                    response = MovieHandler.getMovieList(arrOfStr[1]);
                    break;
                case "getMovieById":
                    response = MovieHandler.getMovieById(arrOfStr[1]);
                    break;
                case "getMoviesByGenre":
                    response = MovieHandler.getMovieByGenre(arrOfStr[1]);
                    break;
                case "getWatchList":
                    response = UserHandler.getWatchList(arrOfStr[1]);
                    break;
                default:
                    System.out.println("Error.InvalidCommand");
                    break;
            }
            System.out.println(response);
        }
    }
}
