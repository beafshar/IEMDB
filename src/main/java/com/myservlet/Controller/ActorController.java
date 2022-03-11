package com.myservlet.Controller;

import com.myservlet.Model.IEMDBController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/actors/*")
public class ActorController extends HttpServlet {
    String actor_id = "";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (IEMDBController.getInstance().getActive_user() == null)
                response.sendRedirect("/login");
            else {
                actor_id = request.getPathInfo().substring(1);
                System.out.println(actor_id);
                request.setAttribute("actor_id", actor_id);
                request.getRequestDispatcher("/actor.jsp").forward(request, response);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

