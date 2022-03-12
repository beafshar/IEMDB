package com.myservlet.Model;

import Model.Error.CommentNotFound;
import java.util.HashMap;
import java.util.Map;

public class CommentHandler {
    public static Map<Integer, Comment> comments = new HashMap<>();
    public static Integer comment_id = 1;

    public static Comment findComment(int comment_id) throws CommentNotFound {
        if (comments.containsKey(comment_id))
            return comments.get(comment_id);
        throw new CommentNotFound();
    }
    public void setComments(Comment[] comments) {
        for (Comment comment : comments) {
            comment.setId(comment_id);
            this.comments.put(comment.getId(), comment);
            MovieHandler.movies.get(comment.getMovieId()).addComment(comment);
            CommentHandler.comment_id++;
        }
    }

}

