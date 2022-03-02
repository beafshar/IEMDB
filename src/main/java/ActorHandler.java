import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import Error.*;

public class ActorHandler {
    public static Map<Integer, Actor> actors = new HashMap<>();

    public ObjectNode addActor(String jsonData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper om = new ObjectMapper();
        ObjectNode response = om.createObjectNode();

        JSONObject json = new JSONObject(jsonData);
        if(actors.containsKey(json.getInt("id")))
            actors.remove(json.getInt("id"));
        Actor actor = objectMapper.readValue(jsonData, Actor.class);
        response.put("success", true);
        response.put("data", "actor added successfully");
        actors.put(json.getInt("id"),actor);
        return response;

    }

    public static Actor findActor(int id){
        return actors.get(id);
    }

    public static ObjectNode ActorNotFound() {
        ActorNotFound err = new ActorNotFound();
        ObjectMapper om = new ObjectMapper();
        ObjectNode response = om.createObjectNode();
        response.put("success", false);
        response.put("data", err.getMessage());
        return response;
    }
    public static Map<Integer, Actor> getActors() { return actors;}
}
