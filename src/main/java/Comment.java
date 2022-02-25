import java.time.LocalDateTime;

public class Comment {
    private String text;
    private String userEmail;
    private int movieId;
    private int id;
    private LocalDateTime recordTime;

    public Comment() {
        id = Handler.comment_id;
        Handler.comment_id++;
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

}
