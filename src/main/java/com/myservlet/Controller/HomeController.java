package com.myservlet.Controller;

import com.myservlet.Model.Error.MovieNotFound;
import com.myservlet.Model.IEMDBController;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Home", value = "")
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (IEMDBController.getInstance().getActive_user() == null)
                response.sendRedirect("/login");
            else
                request.getRequestDispatcher("home.jsp").forward(request, response);
        } catch (InterruptedException | MovieNotFound e) {
            request.getRequestDispatcher("404.html").forward(request, response);
        }
    }
}

