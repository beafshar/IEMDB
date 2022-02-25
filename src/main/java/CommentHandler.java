import org.json.JSONException;
import org.json.JSONObject;
import Error.*;
import java.util.HashSet;
import java.util.Set;

public class CommentHandler {
    public static Set<Comment> comments = new HashSet<Comment>();
    public static Integer comment_id = 1;

    public static JSONObject addComment(String jsonData) throws JSONException {
        addComment AC = new addComment();
        return AC.execute(jsonData);
    }

    public static JSONObject voteComment(String jsonData) throws JSONException {
        JSONObject response = new JSONObject();
        response.put("success", false);
        JSONObject json = new JSONObject(jsonData);
        try {
            for (User user : UserHandler.users) {
                if (user.getEmail().equals(json.getString("userEmail"))) {
                    for (Comment comment : comments) {
                        if (comment.getId() == json.getInt("commentId")) {
                            if (json.getInt("vote") == 0 || json.getInt("vote") == 1 || json.getInt("vote") == -1) {
                                return comment.addVote(user.getEmail(), json.getInt("vote"));
                            }
                            InvalidVoteValue err = new InvalidVoteValue();
                            response.put("data", err.message());
                            return response;
                        }
                    }
                    CommentNotFound err = new CommentNotFound();
                    response.put("data", err.message());
                    return response;
                }
            }
            UserNotFound err = new UserNotFound();
            response.put("data", err.message());
            return response;
        }
        catch (JSONException e) {
            InvalidCommand err = new InvalidCommand();
            response.put("data", err.message());
            return response;
        }
    }
}
