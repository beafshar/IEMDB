package com.myservlet.Model;

import com.myservlet.Model.Error.CommentNotFound;
import com.myservlet.Model.Error.MovieNotFound;
import java.util.HashMap;
import java.util.Map;

public class CommentHandler {
    public static Map<Integer, Comment> comments = new HashMap<>();
    public static Integer comment_id = 1;

    public static Comment findComment(int comment_id) throws CommentNotFound {
        if (comments.containsKey(comment_id))
            return comments.get(comment_id);
        else throw new CommentNotFound();
    }
    public void setComments(Comment[] comments) throws MovieNotFound {
        for (Comment comment : comments) {
            comment.setId(comment_id);
            this.comments.put(comment.getId(), comment);
            MovieHandler.findMovie(comment.getMovieId()).addComment(comment);
            CommentHandler.comment_id++;
        }
    }
    public Comment addComment(String email, Integer movie_id, String text) throws MovieNotFound {
        Comment comment = new Comment(email, movie_id, text);
        comment.setId(comment_id);
        this.comments.put(comment.getId(), comment);
        MovieHandler.findMovie(movie_id).addComment(comment);
        CommentHandler.comment_id++;
        return comment;
    }

}

