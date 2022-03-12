package com.myservlet.Controller;

import com.myservlet.Model.Error.MovieNotFound;
import com.myservlet.Model.IEMDBController;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/watchlist")
public class WatchlistController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (IEMDBController.getInstance().getActive_user() == null)
                response.sendRedirect("/login");
            else
                request.getRequestDispatcher("/watchlist.jsp").forward(request, response);
        } catch (InterruptedException | MovieNotFound e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            IEMDBController.getInstance().getActive_user().removeFromWatchList(Integer.parseInt(request.getParameter("movie_id")));
        } catch (InterruptedException |MovieNotFound e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/watchlist.jsp").forward(request, response);
    }
}

