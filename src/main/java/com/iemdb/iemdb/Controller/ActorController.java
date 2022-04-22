package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.Actor;
import com.iemdb.iemdb.Model.Error.ActorNotFound;
import com.iemdb.iemdb.Model.Error.MovieNotFound;
import com.iemdb.iemdb.Model.IEMDBController;
import com.iemdb.iemdb.Model.Movie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/actors")
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
        return IEMDBController.getInstance().actorHandler.findActor(id);
    }
}
