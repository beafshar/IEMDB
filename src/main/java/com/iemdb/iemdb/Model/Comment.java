package com.iemdb.iemdb.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iemdb.iemdb.Model.Error.InvalidVoteValue;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.beans.ConstructorProperties;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@Getter
public class Comment {
    @Id
    private int id;
    private String text;
    private String userEmail;
    private int movieId;
    private LocalDateTime recordTime;
//    @Type( type = "json" )
//    private final Map<String, Integer> map = new HashMap<>();
    private int likes = 0;
    private int dislikes = 0;

    @ConstructorProperties({"userEmail","movieId","text"})
    @JsonCreator
    public Comment(@JsonProperty(value = "userEmail", required = true) String userEmail,
                   @JsonProperty(value = "movieId", required = true) int movieId,
                   @JsonProperty(value = "text", required = true) String text)
    {
        this.userEmail = userEmail;
        this.movieId = movieId;
        this.text = text;
//        this.id = CommentHandler.comment_id;
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
//        if (vote == 0 || vote == 1 || vote == -1) {
//            if(map.containsKey(userEmail)) {
//                if(map.get(userEmail) == 1)
//                    likes--;
//                if(map.get(userEmail) == -1)
//                    dislikes--;
//            }
//            map.put(userEmail, vote);
//            if(vote == 1)
//                likes++;
//            else if(vote == -1)
//                dislikes++;
//        }
//        else throw new InvalidVoteValue();
    }

    public int getLikes() { return likes; }
    public int getDislikes() { return dislikes; }
}
