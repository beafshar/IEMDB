package com.myservlet.Model;

import com.myservlet.Model.Error.ActorNotFound;
import java.util.HashMap;
import java.util.Map;

public class ActorHandler {
    public static Map<Integer, Actor> actors = new HashMap<>();

    public void setActors(Actor[] actors) {
        for (Actor actor : actors)
            this.actors.put(actor.getId(), actor);
    }
    public static Actor findActor(Integer id) throws ActorNotFound {
        if (actors.containsKey(id))
            return actors.get(id);
        throw new ActorNotFound();
    }

    public static Map<Integer, Actor> getActors() { return actors;}
}
