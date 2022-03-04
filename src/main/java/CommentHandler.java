import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONObject;
import Error.*;
import java.util.HashMap;
import java.util.Map;

public class CommentHandler {
    public static Map<Integer, Comment> comments = new HashMap<>();
    public static Integer comment_id = 1;

    public void setComments(Comment[] comments) {
        for (Comment comment : comments) {
            comment.setId(comment_id);
            this.comments.put(comment.getId(), comment);
            System.out.println(comment.getId());
            MovieHandler.movies.get(comment.getMovieId()).addComment(comment);
            CommentHandler.comment_id++;
        }

    }
    public static ObjectNode addComment(String jsonData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ObjectNode response = objectMapper.createObjectNode();
        Comment comment = objectMapper.readValue(jsonData, Comment.class);
        if(UserHandler.findUser(comment.getUserEmail()) == null)
            return UserHandler.UserNotFound();
        if(MovieHandler.returnMovieObjectGivenId(comment.getMovieId()) == null)
            return MovieHandler.MovieNotFound();

        CommentHandler.comments.put(comment_id, comment);
        MovieHandler.movies.get(comment.getMovieId()).addComment(comment);
        CommentHandler.comment_id++;
        response.put("success", true);
        String res = "comment with id " + comment.getId().toString() + " added successfully";
        response.put("data", res);
        return response;
    }

    public static ObjectNode CommentNotFound() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        response.put("success", false);
        CommentNotFound err = new CommentNotFound();
        response.put("data", err.getMessage());
        return response;
    }

    public static ObjectNode voteComment(String jsonData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        JSONObject json = new JSONObject(jsonData);
        if(UserHandler.findUser(json.getString("userEmail")) != null) {
            if(comments.get(json.getInt("commentId")) != null) {
                if (json.getInt("vote") == 0 || json.getInt("vote") == 1 || json.getInt("vote") == -1) {
                    return comments.get(json.getInt("commentId")).addVote(json.getString("userEmail"), json.getInt("vote"));
                }
                InvalidVoteValue err = new InvalidVoteValue();
                response.put("success", false);
                response.put("data", err.getMessage());
                return response;
            }
            return CommentNotFound();
        }
        return UserHandler.UserNotFound();
    }

}

