package com.myservlet.Controller;

import com.myservlet.Model.IEMDBController;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/movies")
public class MoviesController extends HttpServlet {
    String filter = "";
    String sort_imdb = "1";
    String sort_date = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (IEMDBController.getInstance().getActive_user() == null)
                response.sendRedirect("/login");
            else {
                request.setAttribute("filter", filter);
                request.setAttribute("sort_imdb", sort_imdb);
                request.setAttribute("sort_date", sort_date);
                request.getRequestDispatcher("movies.jsp").forward(request, response);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("search"))
            filter = request.getParameter("search");
        else if (action.equals("clear"))
            filter = "";
        else if (action.equals("sort_by_imdb")) {
            sort_imdb = "1"; sort_date = ""; }
        else if(action.equals("sort_by_date")) {
            sort_date = "1"; sort_imdb = ""; }

        request.setAttribute("filter", filter);
        request.setAttribute("sort_imdb", sort_imdb);
        request.setAttribute("sort_date", sort_date);
        request.getRequestDispatcher("movies.jsp").forward(request, response);
    }
}

