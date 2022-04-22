package com.iemdb.iemdb.Controller;


import com.iemdb.iemdb.Model.Error.MovieNotFound;
import com.iemdb.iemdb.Model.IEMDBController;
import com.iemdb.iemdb.Model.Movie;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/movies")
public class MoviesController {
    String filter = "";
    String sort_imdb = "1";
    String sort_date = "";

    @GetMapping("")
    public ArrayList<Movie>  getMovies() throws IOException, MovieNotFound, InterruptedException {
        return IEMDBController.getInstance().movieHandler.getMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable("id") Integer id) throws IOException, MovieNotFound, InterruptedException {
        System.out.println(id);
        return IEMDBController.getInstance().movieHandler.findMovie(id);
    }

    @GetMapping("/imdb")
    public ArrayList<Movie> sortByIMDB() throws IOException, MovieNotFound, InterruptedException {
        return IEMDBController.getInstance().movieHandler.getMovies(filter, "1", sort_date);
    }

    @GetMapping("/date")
    public ArrayList<Movie> sortByDate() throws IOException, MovieNotFound, InterruptedException {
        return IEMDBController.getInstance().movieHandler.getMovies(filter, sort_imdb, "1");
    }

    @PostMapping("/filter_by_name")
    public ArrayList<Movie> filterByName(@RequestParam(value = "filter", defaultValue = "") String filter) throws IOException, MovieNotFound, InterruptedException {
        return IEMDBController.getInstance().movieHandler.getMovies(filter, sort_imdb, sort_date);
    }

    @PostMapping("/filter_by_genre")
    public ArrayList<Movie> filterByGenre(@RequestParam(value = "filter", defaultValue = "") String filter) throws IOException, MovieNotFound, InterruptedException {
        return IEMDBController.getInstance().movieHandler.getMovies(filter, sort_imdb, sort_date);
    }

}


