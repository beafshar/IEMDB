import java.util.HashSet;
import java.util.Set;

public class ActorHandler {
    public static Set<Actor> actors = new HashSet<Actor>();

    public ActorHandler() {

    }

    public String addActor(String jsonData) {
        addActor AA = new addActor();
        return AA.execute(jsonData);
    }

    public static Set<Actor> getActors() {
        return actors;
    }
}
