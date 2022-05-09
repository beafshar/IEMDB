//package com.iemdb.iemdb.Controller;
//
//import com.iemdb.iemdb.Model.*;
//import com.iemdb.iemdb.Model.Error.*;
//import org.springframework.web.bind.annotation.*;
//import java.io.IOException;
//import java.util.ArrayList;
//
//@RestController
//@RequestMapping("/movies")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//public class MoviesController {
//    String filter = "";
//    String sort_imdb = "1";
//    String sort_date = "";
//
//    @GetMapping("")
//    public ArrayList<Movie>  getMovies() throws IOException, MovieNotFound, InterruptedException {
//        return IEMDBController.getInstance().movieHandler.getMovies();
//    }
//
//    @GetMapping("/imdb")
//    public ArrayList<Movie> sortByIMDB() throws IOException, MovieNotFound, InterruptedException {
//        return IEMDBController.getInstance().movieHandler.getMovies(filter, "1", sort_date);
//    }
//
//    @GetMapping("/date")
//    public ArrayList<Movie> sortByDate() throws IOException, MovieNotFound, InterruptedException {
//        return IEMDBController.getInstance().movieHandler.getMovies(filter, sort_imdb, "1");
//    }
//
//    @GetMapping("/filter_by_name/{filter}")
//    public ArrayList<Movie> filterByName(@PathVariable("filter") String filter) throws IOException, MovieNotFound, InterruptedException {
//        return IEMDBController.getInstance().movieHandler.getMovies(filter, sort_imdb, sort_date);
//    }
//
//    @GetMapping("/filter_by_genre/{filter}")
//    public ArrayList<Movie> filterByGenre(@PathVariable("filter") String filter) throws IOException, MovieNotFound, InterruptedException {
//        return IEMDBController.getInstance().movieHandler.getMovieByGenre(filter);
//    }
//
//    @GetMapping("/filter_by_date/{filter}")
//    public ArrayList<Movie> filterByDate(@PathVariable("filter") String filter) throws IOException, MovieNotFound, InterruptedException {
//        return IEMDBController.getInstance().movieHandler.getMovieByDate(filter);
//    }
//
//    @GetMapping("/{id}")
//    public Movie getMovie(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException {
//        return IEMDBController.getInstance().movieHandler.findMovie(id);
//    }
//
//    @GetMapping("/{id}/actors")
//    public ArrayList<Actor> getActors(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException, ActorNotFound {
//        return IEMDBController.getInstance().movieHandler.findMovie(id).getActors();
//    }
//
//    @GetMapping("/{id}/add_to_watchlist")
//    public void addToWatchlist(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException, AgeLimitError, MovieAlreadyExists {
//        User user = IEMDBController.getInstance().getActive_user();
//        user.addToWatchList(id);
//    }
//
//    @PostMapping("/{id}/rate")
//    public void rateMovie(@PathVariable("id") Integer id, @RequestParam(value = "rate", defaultValue = "") Integer rate) throws MovieNotFound, IOException, InterruptedException, InvalidRateScore {
//        User user = IEMDBController.getInstance().getActive_user();
//        IEMDBController.getInstance().movieHandler.findMovie(id).rateMovie(user.getEmail(), rate);
//    }
//
//    @GetMapping("/{id}/comments")
//    public ArrayList<Comment> getComment(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException {
//        return IEMDBController.getInstance().movieHandler.findMovie(id).getComment();
//    }
//
//    @PostMapping("/{id}/comment")
//    public Comment addComment(@PathVariable("id") Integer id, @RequestParam(value = "comment", defaultValue = "") String comment) throws IOException, MovieNotFound, InterruptedException {
//        User user = IEMDBController.getInstance().getActive_user();
//        return IEMDBController.getInstance().commentHandler.addComment(user.getEmail(), id, comment);
//    }
//
//    @GetMapping("/{id}/{comment_id}/like")
//    public void likeComment(@PathVariable("id") Integer id, @PathVariable("comment_id") Integer comment_id) throws MovieNotFound, IOException, InterruptedException, CommentNotFound, InvalidVoteValue {
//        Comment comment = IEMDBController.getInstance().commentHandler.findComment(comment_id);
//        User user = IEMDBController.getInstance().getActive_user();
//        comment.addVote(user.getEmail(), 1);
//    }
//
//    @GetMapping("/{id}/{comment_id}/dislike")
//    public void dislikeComment(@PathVariable("id") Integer id, @PathVariable("comment_id") Integer comment_id) throws MovieNotFound, IOException, InterruptedException, CommentNotFound, InvalidVoteValue {
//        Comment comment = IEMDBController.getInstance().commentHandler.findComment(comment_id);
//        User user = IEMDBController.getInstance().getActive_user();
//        comment.addVote(user.getEmail(), -1);
//    }
//
//}