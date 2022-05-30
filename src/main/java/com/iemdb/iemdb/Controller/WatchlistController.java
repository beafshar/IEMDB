package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.Error.*;
import com.iemdb.iemdb.Model.*;
import com.iemdb.iemdb.Repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/watchlist")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
public class WatchlistController {
    private MovieRepository movieRepo;
    private IEMDBController iemdbController;

    @GetMapping("")
    public ArrayList<Movie> getWatchlist() throws IOException, MovieNotFound, InterruptedException {
        User user = iemdbController.getActive_user();
        return user.getWatchlist();
    }

    @PostMapping("")
    public void removeMovieFromWatchlist(@RequestParam(value = "id", defaultValue = "") Integer id) throws IOException, MovieNotFound, InterruptedException {
        User user = iemdbController.getActive_user();
        Movie movie = movieRepo.findAllById(id);
        user.removeFromWatchList(movie);
    }
    @GetMapping("/recommendations")
    public List<Movie> getRecommendationList() throws IOException, MovieNotFound, InterruptedException {
        User user = iemdbController.getActive_user();
        return user.getRecommendationList((List<Movie>) movieRepo.findAll());
    }


}
