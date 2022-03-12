package com.myservlet.Controller;

import com.myservlet.Model.IEMDBController;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        try {
            IEMDBController.getInstance().setActive_user(email);
            response.sendRedirect("/");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}