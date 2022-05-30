package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.*;
import com.iemdb.iemdb.Model.Error.*;
import com.iemdb.iemdb.Repository.ActorRepository;
import com.iemdb.iemdb.Repository.CommentRepository;
import com.iemdb.iemdb.Repository.MovieRepository;
import com.iemdb.iemdb.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Streamable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
public class MoviesController {
    private IEMDBController iemdbController;
    private MovieRepository movieRepo;
    private ActorRepository actorRepo;
    private UserRepository userRepo;
    private CommentRepository commentRepo;

    @GetMapping("")
    public Iterable<Movie> getMovies() throws IOException, MovieNotFound, InterruptedException {
        return movieRepo.findAll();
    }

    @GetMapping("/imdb")
    public Iterable<Movie> sortByIMDB() throws IOException, MovieNotFound, InterruptedException {
        return movieRepo.findAllByOrderByImdbRateDesc();
    }

    @GetMapping("/date")
    public Iterable<Movie> sortByDate() throws IOException, MovieNotFound, InterruptedException {
        return movieRepo.findAllByOrderByReleaseDateDesc();
    }

    @GetMapping("/filter_by_name/{filter}")
    public List<Movie> filterByName(@PathVariable("filter") String filter) throws IOException, MovieNotFound, InterruptedException {
        return movieRepo.findAllByNameContains(filter);
    }

    @GetMapping("/filter_by_genre/{filter}")
    public List<Movie> filterByGenre(@PathVariable("filter") String filter) throws IOException, MovieNotFound, InterruptedException {
        return movieRepo.findAllByGenresContains(filter);
    }

    @GetMapping("/filter_by_date/{filter}")
    public List<Movie> filterByDate(@PathVariable("filter") String filter) throws IOException, MovieNotFound, InterruptedException {
        return movieRepo.findAllByReleaseDateAfter(filter);
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException {
        return movieRepo.findAllById(id);
    }

    @GetMapping("/{id}/actors")
    public List<Actor> getActors(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException, ActorNotFound {
        return actorRepo.findAllByMovies(movieRepo.findAllById(id));
    }

    @GetMapping("/{id}/add_to_watchlist")
    public void addToWatchlist(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException, AgeLimitError, MovieAlreadyExists {
        User user = iemdbController.getActive_user();
        Movie movie = movieRepo.findAllById(id);
        user.addToWatchList(movie);
        userRepo.save(user);
    }

    @PostMapping("/{id}/rate")
    public void rateMovie(@PathVariable("id") Integer id, @RequestParam(value = "rate", defaultValue = "") Integer rate) throws MovieNotFound, IOException, InterruptedException, InvalidRateScore {
        User user = IEMDBController.getActive_user();
        Movie movie = movieRepo.findAllById(id);
        movie.rateMovie(user.getEmail(), rate);
        movieRepo.save(movie);

    }

    @GetMapping("/{id}/comments")
    public List<Comment> getComment(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException {
        return movieRepo.findAllById(id).getComments();
    }

    @PostMapping("/{id}/comment")
    public void addComment(@PathVariable("id") Integer id, @RequestParam("text") String text) throws IOException, MovieNotFound, InterruptedException {
        User user = iemdbController.getActive_user();
        System.out.println(text);
        Comment comment = new Comment(user.getEmail(),id,text);
        Movie movie = movieRepo.findAllById(id);
        movie.addComment(comment);
        commentRepo.save(comment);
        movieRepo.save(movie);

    }

    @GetMapping("/{id}/{comment_id}/like")
    public void likeComment(@PathVariable("id") Integer id, @PathVariable("comment_id") Integer comment_id) throws MovieNotFound, IOException, InterruptedException, CommentNotFound, InvalidVoteValue {
        User user = iemdbController.getActive_user();
        Comment comment = commentRepo.findAllById(comment_id);
        comment.addVote(user.getEmail(), 1);
        commentRepo.save(comment);
    }

    @GetMapping("/{id}/{comment_id}/dislike")
    public void dislikeComment(@PathVariable("id") Integer id, @PathVariable("comment_id") Integer comment_id) throws MovieNotFound, IOException, InterruptedException, CommentNotFound, InvalidVoteValue {
        User user = iemdbController.getActive_user();
        Comment comment = commentRepo.findAllById(comment_id);
        comment.addVote(user.getEmail(), -1);
        commentRepo.save(comment);
    }

}