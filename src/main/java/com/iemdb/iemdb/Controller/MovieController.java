package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.Error.MovieNotFound;
import com.iemdb.iemdb.Model.IEMDBController;
import com.iemdb.iemdb.Model.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

//@RestController
//@RequestMapping("/movies/{id}")
//public class MovieController {
//    String movie_id = "";
//    @GetMapping("/")
//    public Movie getMovie(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException {
//        System.out.println(id);
//        return IEMDBController.getInstance().movieHandler.findMovie(id);
//    }

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            if (IEMDBController.getInstance().getActive_user() == null)
//                response.sendRedirect("/login");
//            else {
//                movie_id = request.getPathInfo().substring(1);
//                request.setAttribute("movie_id", movie_id);
//                request.getRequestDispatcher("/movie.jsp").forward(request, response);
//            }
//        } catch (InterruptedException |MovieNotFound e) {
//            request.getRequestDispatcher("404.html").forward(request, response);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        movie_id = request.getPathInfo().substring(1);
//        request.setAttribute("movie_id", movie_id);
//        String action = request.getParameter("action");
//        try {
//            if (action.equals("add"))
//                IEMDBController.getInstance().getActive_user().addToWatchList(Integer.parseInt(request.getParameter("movie_id")));
//            else if (action.equals("comment"))
//                IEMDBController.commentHandler.addComment(IEMDBController.getInstance().getActive_user().getEmail(),
//                        Integer.parseInt(request.getParameter("movie_id")), request.getParameter("comment"));
//            else if (action.equals("rate"))
//                IEMDBController.movieHandler.findMovie(Integer.parseInt(request.getParameter("movie_id"))).rateMovie(
//                        IEMDBController.getInstance().getActive_user().getEmail(), Integer.parseInt(request.getParameter("quantity")));
//            else if (action.equals("like"))
//                IEMDBController.commentHandler.findComment(Integer.parseInt(request.getParameter("comment_id"))).addVote(
//                        IEMDBController.getInstance().getActive_user().getEmail(), 1);
//            else if (action.equals("dislike"))
//                IEMDBController.commentHandler.findComment(Integer.parseInt(request.getParameter("comment_id"))).addVote(
//                        IEMDBController.getInstance().getActive_user().getEmail(), -1);
//            request.getRequestDispatcher("/movie.jsp").forward(request, response);
//        } catch (InterruptedException | CommentNotFound | MovieNotFound e) {
//            request.getRequestDispatcher("404.html").forward(request, response);
//        }
//        catch (InvalidRateScore | InvalidVoteValue | AgeLimitError | MovieAlreadyExists e) {
//            request.setAttribute("error", e.getMessage());
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//        }
//    }
//}
