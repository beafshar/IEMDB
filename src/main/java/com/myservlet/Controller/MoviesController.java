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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (IEMDBController.getInstance().getActive_user() == null)
                response.sendRedirect("/login");
            else {
                request.setAttribute("filter", filter);
                request.getRequestDispatcher("movies.jsp").forward(request, response);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equals("search")) {
            filter = request.getParameter("search");
        }
        else if (action.equals("clear")) {
            filter = "";
        }

        request.setAttribute("filter", filter);
        System.out.println("Filter: " + filter);
        request.getRequestDispatcher("movies.jsp").forward(request, response);
    }
}

