import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import Error.*;
import org.json.JSONException;
import org.json.JSONObject;

public class addComment {
    public JSONObject execute(String jsonData) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Comment comment = objectMapper.readValue(jsonData, Comment.class);
            JSONObject response = new JSONObject();
            response = checkUser(comment);
            if (response != null)
                return response;
            response = checkMovie(comment);

            if (response != null)
                return response;

            response = new JSONObject();
            CommentHandler.comments.add(comment);
            response.put("success", true);
            String res = "comment with id " + comment.getId().toString() + " added successfully";
            response.put("data", res);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private JSONObject checkUser(Comment comment) throws JSONException {
        for (User user : UserHandler.users) {
            if (comment.getUserEmail().equals(user.getEmail())) {
                return null;
            }
        }
        UserNotFound err = new UserNotFound();
        JSONObject r = new JSONObject();
        r.put("success", false);
        r.put("data", err.message());
        return r;
    }

    private JSONObject checkMovie(Comment comment) throws JSONException {
        for (Movie movie : MovieHandler.movies) {
            if (comment.getMovieId() == movie.getId()) {
                movie.addComment(comment);
                return null;
            }
        }
        MovieNotFound err = new MovieNotFound();
        JSONObject r = new JSONObject();
        r.put("success", false);
        r.put("data", err.message());
        return r;
    }
}