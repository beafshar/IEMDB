package com.iemdb.iemdb.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping("")
    public String login() throws IOException {
        return "login";
    }
}
//public class LoginController{
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getRequestDispatcher("login.jsp").forward(request, response);
//    }
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String email = request.getParameter("email");
//        try {
//            IEMDBController.getInstance().setActive_user(email);
//            response.sendRedirect("/");
//        } catch (InterruptedException | MovieNotFound | UserNotFound e) {
//            request.getRequestDispatcher("404.html").forward(request, response);
//        }
//    }
//}