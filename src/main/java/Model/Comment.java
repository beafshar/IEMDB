package Model;

import Model.Error.InvalidVoteValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import Model.Error.*;

public class Comment {
    private String text;
    private String userEmail;
    private int movieId;
    private int id;
    private LocalDateTime recordTime;
    private Map<String, Integer> map = new HashMap<>();
    private int likes = 0;
    private int dislikes = 0;

    @ConstructorProperties({"userEmail","movieId","text"})
    @JsonCreator
    public Comment(@JsonProperty(value = "userEmail", required = true) String userEmail,
                   @JsonProperty(value = "movieId", required = true) int movieId,
                   @JsonProperty(value = "text", required = true) String text) {
        this.userEmail = userEmail;
        this.movieId = movieId;
        this.text = text;
        this.id = CommentHandler.comment_id;
        this.recordTime = LocalDateTime.now();
    }

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId() { return this.id; }
    public String getText() { return this.text; }
    public String getUserEmail() { return this.userEmail; }
    public int getMovieId() { return this.movieId; }
    public LocalDateTime getRecordTime() { return this.recordTime; }

    public ObjectNode addVote(String userEmail, int vote) throws InvalidVoteValue {
        if (vote == 0 || vote == 1 || vote == -1) {
            if(map.containsKey(userEmail)) {
                if(map.get(userEmail) == 1)
                    likes--;
                if(map.get(userEmail) == -1)
                    dislikes--;
            }
            map.put(userEmail, vote);
            if(vote == 1)
                likes++;
            else if(vote == -1)
                dislikes++;
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode response = objectMapper.createObjectNode();
            response.put("success", true);
            response.put("data", "comment voted successfully");
            return response;
        }
        throw new InvalidVoteValue();

    }

    public int getLikes() { return likes; }
    public int getDislikes() { return dislikes; }
}
