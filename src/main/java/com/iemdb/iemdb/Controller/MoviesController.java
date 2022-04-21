package com.iemdb.iemdb.Controller;


import com.iemdb.iemdb.Model.Error.MovieNotFound;
import com.iemdb.iemdb.Model.IEMDBController;
import com.iemdb.iemdb.Model.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/movies")
public class MoviesController {
    String filter = "";
    String sort_imdb = "1";
    String sort_date = "";

    @GetMapping("")
    public ArrayList<Movie> getMovies() throws IOException, MovieNotFound, InterruptedException {
        return IEMDBController.getInstance().movieHandler.getMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException {
        System.out.println(id);
        return IEMDBController.getInstance().movieHandler.findMovie(id);
    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            if (IEMDBController.getInstance().getActive_user() == null)
//                response.sendRedirect("/login");
//            else {
//                request.setAttribute("filter", filter);
//                request.setAttribute("sort_imdb", sort_imdb);
//                request.setAttribute("sort_date", sort_date);
//                request.getRequestDispatcher("movies.jsp").forward(request, response);
//            }
//        } catch (InterruptedException | MovieNotFound e) {
//            request.getRequestDispatcher("404.html").forward(request, response);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String action = request.getParameter("action");
//
//        if (action.equals("search"))
//            filter = request.getParameter("search");
//        else if (action.equals("clear"))
//            filter = "";
//        else if (action.equals("sort_by_imdb")) {
//            sort_imdb = "1"; sort_date = ""; }
//        else if(action.equals("sort_by_date")) {
//            sort_date = "1"; sort_imdb = ""; }
//
//        request.setAttribute("filter", filter);
//        request.setAttribute("sort_imdb", sort_imdb);
//        request.setAttribute("sort_date", sort_date);
//        request.getRequestDispatcher("movies.jsp").forward(request, response);
//    }
}

