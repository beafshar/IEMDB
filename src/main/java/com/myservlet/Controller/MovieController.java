package com.myservlet.Controller;

import com.myservlet.Model.Comment;
import com.myservlet.Model.Error.*;
import com.myservlet.Model.IEMDBController;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/movies/*")
public class MovieController extends HttpServlet {
    String movie_id = "";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (IEMDBController.getInstance().getActive_user() == null)
                response.sendRedirect("/login");
            else {
                movie_id = request.getPathInfo().substring(1);
                request.setAttribute("movie_id", movie_id);
                request.getRequestDispatcher("/movie.jsp").forward(request, response);
            }
        } catch (InterruptedException |MovieNotFound e) {
            request.getRequestDispatcher("404.html").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        movie_id = request.getPathInfo().substring(1);
        request.setAttribute("movie_id", movie_id);
        String action = request.getParameter("action");
        try {
            if (action.equals("add"))
                IEMDBController.getInstance().getActive_user().addToWatchList(Integer.parseInt(request.getParameter("movie_id")));
            else if (action.equals("comment"))
                IEMDBController.commentHandler.addComment(IEMDBController.getInstance().getActive_user().getEmail(),
                        Integer.parseInt(request.getParameter("movie_id")), request.getParameter("comment"));
            else if (action.equals("rate"))
                IEMDBController.movieHandler.findMovie(Integer.parseInt(request.getParameter("movie_id"))).rateMovie(
                        IEMDBController.getInstance().getActive_user().getEmail(), Integer.parseInt(request.getParameter("quantity")));
            else if (action.equals("like"))
                IEMDBController.commentHandler.findComment(Integer.parseInt(request.getParameter("comment_id"))).addVote(
                        IEMDBController.getInstance().getActive_user().getEmail(), 1);
            else if (action.equals("dislike"))
                IEMDBController.commentHandler.findComment(Integer.parseInt(request.getParameter("comment_id"))).addVote(
                        IEMDBController.getInstance().getActive_user().getEmail(), -1);
            request.getRequestDispatcher("/movie.jsp").forward(request, response);
        } catch (InterruptedException | CommentNotFound | MovieNotFound e) {
            request.getRequestDispatcher("404.html").forward(request, response);
        }
        catch (InvalidRateScore | InvalidVoteValue | AgeLimitError | MovieAlreadyExists e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
