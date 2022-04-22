package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.*;
import com.iemdb.iemdb.Model.Error.*;
import org.springframework.web.bind.annotation.*;
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
