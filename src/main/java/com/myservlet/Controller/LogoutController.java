package com.myservlet.Controller;

import com.myservlet.Model.Error.MovieNotFound;
import com.myservlet.Model.Error.UserNotFound;
import com.myservlet.Model.IEMDBController;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            IEMDBController.getInstance().setActive_user(null);
            response.sendRedirect("/login");
        } catch (InterruptedException | MovieNotFound | UserNotFound e) {
            e.printStackTrace();
        }
    }
}