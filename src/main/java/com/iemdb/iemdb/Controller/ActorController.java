package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.*;
import com.iemdb.iemdb.Model.Error.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/actors")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ActorController {

    @GetMapping("")
    public ArrayList<Actor> getActors() throws IOException, InterruptedException, MovieNotFound {
        try {
            return IEMDBController.getInstance().actorHandler.getActors();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/{id}")
    public Actor getActor(@PathVariable("id") Integer id) throws MovieNotFound, IOException, InterruptedException, ActorNotFound {
        return IEMDBController.getInstance().actorHandler.findActor(id.intValue());
    }

    @GetMapping("/{id}/movies")
    public ArrayList<Movie> getMovies(@PathVariable("id") Integer id) throws IOException, InterruptedException, MovieNotFound, ActorNotFound {
        List<Integer> movies =  IEMDBController.getInstance().actorHandler.findActor(id).getMovies();
        ArrayList<Movie> movieList = new ArrayList<>();
        for (Integer movie : movies) {
            movieList.add(IEMDBController.getInstance().movieHandler.findMovie(movie));
        }
        return movieList;
    }
}
