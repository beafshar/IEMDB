package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.Error.MovieNotFound;
import com.iemdb.iemdb.Model.IEMDBController;
import com.iemdb.iemdb.Model.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

    @GetMapping("")
    public ArrayList<Movie> getMovies() throws IOException, MovieNotFound, InterruptedException {
        return IEMDBController.getInstance().movieHandler.getMovies();
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            if (IEMDBController.getInstance().getActive_user() == null)
//                response.sendRedirect("/login");
//            else
//                request.getRequestDispatcher("/watchlist.jsp").forward(request, response);
//        } catch (InterruptedException | MovieNotFound e) {
//            request.getRequestDispatcher("404.html").forward(request, response);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            IEMDBController.getInstance().getActive_user().removeFromWatchList(Integer.parseInt(request.getParameter("movie_id")));
//            request.getRequestDispatcher("/watchlist.jsp").forward(request, response);
//        } catch (InterruptedException |MovieNotFound e) {
//            request.getRequestDispatcher("404.html").forward(request, response);
//        }
//    }
}

