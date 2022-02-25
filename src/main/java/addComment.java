import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import Error.*;

public class addComment {
    public String execute(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Comment comment = objectMapper.readValue(jsonData, Comment.class);
            String response = checkUser(comment);
            if (response != null)
                return response;
            response = checkMovie(comment);
            if (response != null)
                return response;

            return "{\"success\": ture, \"data\": \"comment with id " + comment.getId().toString() + " added successfully\"}";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String checkUser(Comment comment) {
        for (User user : UserHandler.users) {
            if (comment.getUserEmail().equals(user.getEmail())) {
                return null;
            }
        }
        UserNotFound err = new UserNotFound();
        return "{\"success\": false, \"data\": " + err.message() + "\"}";
    }

    private String checkMovie(Comment comment) {
        for (Movie movie : MovieHandler.movies) {
            if (comment.getMovieId() == movie.getId()) {
                movie.addComment(comment);
                return null;
            }
        }
        MovieNotFound err = new MovieNotFound();
        return "{\"success\": false, \"data\": " + err.message() + "\"}";
    }
}