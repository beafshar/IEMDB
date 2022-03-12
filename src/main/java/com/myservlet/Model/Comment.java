package com.myservlet.Model;

import com.myservlet.Model.Error.InvalidVoteValue;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class Comment {
    private final String text;
    private final String userEmail;
    private final int movieId;
    private int id;
    private final LocalDateTime recordTime;
    private final Map<String, Integer> map = new HashMap<>();
    private int likes = 0;
    private int dislikes = 0;

    public Comment( String userEmail, int movieId, String text) {
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

    public void addVote(String userEmail, int vote) throws InvalidVoteValue {
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
        }
        throw new InvalidVoteValue();
    }

    public int getLikes() { return likes; }
    public int getDislikes() { return dislikes; }
}
