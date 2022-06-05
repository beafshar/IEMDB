package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.*;
import com.iemdb.iemdb.Model.Error.*;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/movies")
//@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class MoviesController {
    String filter_name = null;
    String filter_genre = null;
    String filter_year = null;
    String sort_imdb = "1";
    String sort_date = null;

    @GetMapping("")
    public ArrayList<Movie>  getMovies() throws IOException, MovieNotFound, InterruptedException {
        try {
            return IEMDBController.getInstance().movieHandler.getMovies(null, null, sort_imdb, null);
        } catch (IOException | MovieNotFound | InterruptedException e) {
            return null;
        }
    }

    @GetMapping("/imdb")
    public ArrayList<Movie> sortByIMDB() throws IOException, MovieNotFound, InterruptedException {
        try {
            sort_imdb = "1";
            sort_date = null;
            return IEMDBController.getInstance().movieHandler.getMovies(filter_name, filter_genre, "1", sort_date);
        } catch (IOException | MovieNotFound | InterruptedException e) {
            return null;
        }

    }

    @GetMapping("/date")
    public ArrayList<Movie> sortByDate() throws IOException, MovieNotFound, InterruptedException {
        try {
            sort_imdb = null;
            sort_date = "1";
            return IEMDBController.getInstance().movieHandler.getMovies(filter_name, filter_genre,sort_imdb, "1");
        } catch (IOException | MovieNotFound | InterruptedException e) {
            return null;
        }
    }

    @GetMapping("/filter_by_name/{filter}")
    public ArrayList<Movie> filterByName(@PathVariable("filter") String filter) throws IOException, MovieNotFound, InterruptedException {
        try {
            filter_name = filter;
            filter_genre = null;
            filter_year = null;
            return IEMDBController.getInstance().movieHandler.getMovies(filter, filter_genre, sort_imdb, sort_date);
        } catch (IOException | MovieNotFound | InterruptedException e) {
            return null;
        }
    }

    @GetMapping("/filter_by_genre/{filter}")
    public ArrayList<Movie> filterByGenre(@PathVariable("filter") String filter) throws IOException, MovieNotFound, InterruptedException {
        System.out.println("genre" + filter);
        try {
            System.out.println(filter);
            filter_name = null;
            filter_genre = filter;
            filter_year = null;
            return IEMDBController.getInstance().movieHandler.getMovies(filter_name, filter, sort_imdb, sort_date);
        } catch (IOException | MovieNotFound | InterruptedException e) {
            return null;
        }
    }

    @GetMapping("/filter_by_date/{filter}")
    public ArrayList<Movie> filterByDate(@PathVariable("filter") String filter) throws IOException, MovieNotFound, InterruptedException {
        try {
            filter_name = null;
            filter_genre = null;
            filter_year = filter;
            return IEMDBController.getInstance().movieHandler.getMovies(filter, filter_genre, sort_imdb, sort_date);
        } catch (IOException | MovieNotFound | InterruptedException e) {
            return null;
        }
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException {
        try {
            return IEMDBController.getInstance().movieHandler.findMovie(id);
        } catch (IOException | MovieNotFound | InterruptedException e) {
            return null;
        }
    }

    @GetMapping("/{id}/actors")
    public ArrayList<Actor> getActors(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException, ActorNotFound {
        try {
            return IEMDBController.getInstance().movieHandler.findMovie(id).getActors();
        } catch (IOException | MovieNotFound | InterruptedException | ActorNotFound e) {
            return null;
        }
    }

    @GetMapping("/{id}/add_to_watchlist")
    public void addToWatchlist(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException, AgeLimitError, MovieAlreadyExists {
        try {
            User user = IEMDBController.getInstance().getActive_user();
            user.addToWatchList(id);
        } catch (IOException | MovieNotFound | InterruptedException | AgeLimitError | MovieAlreadyExists e) {
            return;
        }
    }

    @PostMapping("/{id}/rate/{rate}")
    public void rateMovie(@PathVariable("id") Integer id, @PathVariable("rate") Integer rate) throws MovieNotFound, IOException, InterruptedException, InvalidRateScore {
        try {
            User user = IEMDBController.getInstance().getActive_user();
            IEMDBController.getInstance().movieHandler.findMovie(id).rateMovie(user.getEmail(), rate);
        } catch (IOException | MovieNotFound | InterruptedException | InvalidRateScore e) {
            return;
        }
    }

    @GetMapping("/{id}/comments")
    public ArrayList<Comment> getComment(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException {
        try {
            return IEMDBController.getInstance().movieHandler.findMovie(id).getComment();
        } catch (IOException | MovieNotFound | InterruptedException e) {
            return null;
        }
    }

    @PostMapping("/{id}/comment/{comment}")
    public Comment addComment(@PathVariable("id") Integer id, @PathVariable("comment") String comment) throws IOException, MovieNotFound, InterruptedException {
        try {
            User user = IEMDBController.getInstance().getActive_user();
            return IEMDBController.getInstance().commentHandler.addComment(user.getEmail(), id, comment);
        } catch (IOException | MovieNotFound | InterruptedException e) {
            return null;
        }
    }

    @GetMapping("/{id}/{comment_id}/like")
    public void likeComment(@PathVariable("id") Integer id, @PathVariable("comment_id") Integer comment_id) throws MovieNotFound, IOException, InterruptedException, CommentNotFound, InvalidVoteValue {
        try {
            Comment comment = IEMDBController.getInstance().commentHandler.findComment(comment_id);
            User user = IEMDBController.getInstance().getActive_user();
            comment.addVote(user.getEmail(), 1);
        } catch (IOException | MovieNotFound | InterruptedException | CommentNotFound | InvalidVoteValue e) {
            return;
        }
    }

    @GetMapping("/{id}/{comment_id}/dislike")
    public void dislikeComment(@PathVariable("id") Integer id, @PathVariable("comment_id") Integer comment_id) throws MovieNotFound, IOException, InterruptedException, CommentNotFound, InvalidVoteValue {
        try {
            Comment comment = IEMDBController.getInstance().commentHandler.findComment(comment_id);
            User user = IEMDBController.getInstance().getActive_user();
            comment.addVote(user.getEmail(), -1);
        } catch (IOException | MovieNotFound | InterruptedException | CommentNotFound | InvalidVoteValue e) {
            return;
        }
    }

}