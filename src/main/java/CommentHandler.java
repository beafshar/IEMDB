import org.json.JSONException;
import org.json.JSONObject;
import Error.*;
import java.util.HashSet;
import java.util.Set;

public class CommentHandler {
    public static Set<Comment> comments = new HashSet<Comment>();
    public static Integer comment_id = 1;

    public static String addComment(String jsonData) {
        addComment AC = new addComment();
        return AC.execute(jsonData);
    }

    public static String voteComment(String jsonData) throws JSONException {
        JSONObject json = new JSONObject(jsonData);
        for(User user: UserHandler.users) {
            if(user.getEmail().equals(json.getString("userEmail"))) {
                for(Comment comment: comments) {
                    if(comment.getId() == json.getInt("commentId")) {
                        if(json.getInt("vote") == 0 || json.getInt("vote") == 1 || json.getInt("vote") == -1) {
                            return comment.addVote(user.getEmail(), json.getInt("vote"));
                        }
                        InvalidVoteValue err = new InvalidVoteValue();
                        return "{\"success\": false, \"data\": " + err.message() + "\"}";
                    }
                }
                CommentNotFound err = new CommentNotFound();
                return "{\"success\": false, \"data\": " + err.message() + "\"}";
            }
        }
        UserNotFound err = new UserNotFound();
        return "{\"success\": false, \"data\": " + err.message() + "\"}";
    }
}
