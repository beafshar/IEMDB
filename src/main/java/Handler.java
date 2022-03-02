import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Handler {

    private static ActorHandler actorHandler = new ActorHandler();
//    private static UserHandler userHandler = new UserHandler();
//    private static MovieHandler movieHandler = new MovieHandler();
//    private static CommentHandler commentHandler = new CommentHandler();
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null)
        {
            String[] arrOfStr = line.split(" ", 2);
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode response = objectMapper.createObjectNode();

            switch(arrOfStr[0]) {

                case "addActor":
                    response = actorHandler.addActor(arrOfStr[1]);
                    break;
//                case "addMovie":
//                    response = movieHandler.addMovie(arrOfStr[1]);
//                    break;
//                case "addUser":
//                    response = userHandler.addUser(arrOfStr[1]);
//                    break;
//                case "addComment":
//                    response = commentHandler.addComment(arrOfStr[1]);
//                    break;
//                case "rateMovie":
//                    response = movieHandler.rateMovie(arrOfStr[1]);
//                    break;
//                case "voteComment":
//                    response = commentHandler.voteComment(arrOfStr[1]);
//                    break;
//                case "addToWatchList":
//                    response = userHandler.addToWatchList(arrOfStr[1]);
//                    break;
//                case "removeFromWatchList":
//                    response = userHandler.removeFromWatchList(arrOfStr[1]);
//                    break;
//                case "getMoviesList":
//                    response = movieHandler.getMovieList();
//                    break;
//                case "getMovieById":
//                    response = movieHandler.getMovieById(arrOfStr[1]);
//                    break;
//                case "getMoviesByGenre":
//                    response = movieHandler.getMovieByGenre(arrOfStr[1]);
//                    break;
//                case "getWatchList":
//                    response = userHandler.getWatchList(arrOfStr[1]);
//                    break;
                default:
                    System.out.println("Error.InvalidCommand");
                    break;
            }
            System.out.println(response);
        }
    }
}
