import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Comment {
    private String text;
    private String userEmail;
    private int movieId;
    private int id;
    private LocalDateTime recordTime;
    private Map<String, Integer> map = new HashMap<String, Integer>();
    private int likes = 0;
    private int dislikes = 0;

    public Comment() {
        id = CommentHandler.comment_id;
        recordTime = LocalDateTime.now();
    }
    public Comment(String _userEmail, int _movieId, String _text) {
        userEmail = _userEmail;
        movieId = _movieId;
        text = _text;

    }

    public Integer getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public int getMovieId() {
        return movieId;
    }
    public LocalDateTime getRecordTime() {
        return recordTime;
    }

    public JSONObject addVote(String userEmail, int vote) throws JSONException {
        if(map.containsKey(userEmail)) {
            if(map.get(userEmail) == 1)
                likes--;
            if(map.get(userEmail) == -1)
                dislikes--;
        }
        map.put(userEmail, vote);
        if(vote == 1)
            likes++;
        if(vote == -1)
            dislikes++;
        JSONObject response = new JSONObject();
        response.put("success", true);
        response.put("data", "comment voted successfully");
        return response;
    }

    public int getLikes() {
        return likes;
    }
    public int getDislikes() {
        return dislikes;
    }
}
