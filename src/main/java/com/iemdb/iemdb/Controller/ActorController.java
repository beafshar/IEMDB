package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.*;
import com.iemdb.iemdb.Model.Error.*;
import com.iemdb.iemdb.Repository.ActorRepository;
import com.iemdb.iemdb.Repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/actors")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
public class ActorController {
    private ActorRepository actorRepo;
    private MovieRepository movieRepo;

    @GetMapping("")
    public Iterable<Actor> getActors() throws IOException, InterruptedException, MovieNotFound {

            return actorRepo.findAll();
    }

    @GetMapping("/{id}")
    public Actor getActor(@PathVariable("id") Integer id) throws MovieNotFound, IOException, InterruptedException, ActorNotFound {
        return actorRepo.findAllById(id);
    }

    @GetMapping("/{id}/movies")
    public List<Movie> getMovies(@PathVariable("id") Integer id) throws IOException, InterruptedException, MovieNotFound, ActorNotFound {
        return movieRepo.findAllByCastId(id);
    }
}
