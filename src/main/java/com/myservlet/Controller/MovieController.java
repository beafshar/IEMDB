package com.myservlet.Controller;

import Model.Error.AgeLimitError;
import Model.Error.MovieAlreadyExists;
import Model.Error.MovieNotFound;
import com.myservlet.Model.Comment;
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
        } catch (InterruptedException e) {
            e.printStackTrace();
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
            else if (action.equals("comment")) {
                Comment comment = new Comment(IEMDBController.getInstance().getActive_user().getEmail(),
                        Integer.parseInt(request.getParameter("movie_id")),
                        request.getParameter("comment"));
                IEMDBController.movieHandler.movies.get(Integer.parseInt(request.getParameter("movie_id"))).addComment(comment);
            }

        } catch (MovieAlreadyExists | InterruptedException | AgeLimitError | MovieNotFound e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/movie.jsp").forward(request, response);
    }
}

