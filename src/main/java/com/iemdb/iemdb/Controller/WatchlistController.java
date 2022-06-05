package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.Error.*;
import com.iemdb.iemdb.Model.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/watchlist")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WatchlistController {

    @GetMapping("")
    public ArrayList<Movie> getWatchlist() throws IOException, MovieNotFound, InterruptedException {
        try {
            User user = IEMDBController.getInstance().getActive_user();
            if (user == null)
                return null;
            return user.getWatchlist();
        }
        catch ( IOException | MovieNotFound | InterruptedException e){
            return null;
        }
    }

    @GetMapping("/recommendations")
    public ArrayList<Movie> getRecommendationList() throws IOException, MovieNotFound, InterruptedException {
        try {
            User user = IEMDBController.getInstance().getActive_user();
            if (user == null)
                return null;
            return (ArrayList<Movie>) user.getRecommendationList();
        }
        catch ( IOException | MovieNotFound | InterruptedException e){
            return null;
        }
    }

    @PostMapping("/{id}")
    public void removeMovieFromWatchlist(@PathVariable Integer id) throws IOException, MovieNotFound, InterruptedException {
        try {
            User user = IEMDBController.getInstance().getActive_user();
            user.removeFromWatchList(id);
        }
        catch ( IOException | MovieNotFound | InterruptedException e){
            return;
        }
    }
}
