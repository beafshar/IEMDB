package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.Error.*;
import com.iemdb.iemdb.Model.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/watchlist")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WatchlistController {

    @GetMapping("")
    public ArrayList<Movie> getWatchlist() throws IOException, MovieNotFound, InterruptedException {
        User user = IEMDBController.getInstance().getActive_user();
        return user.getWatchlist();
    }

    @PostMapping("")
    public void removeMovieFromWatchlist(@RequestParam(value = "id", defaultValue = "") Integer id) throws IOException, MovieNotFound, InterruptedException {
        User user = IEMDBController.getInstance().getActive_user();
        user.removeFromWatchList(id);
    }
}
