import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import Error.*;

public class ActorHandler {
    public static Map<Integer, Actor> actors = new HashMap<>();

    public ActorHandler() {
    }

    public ObjectNode addActor(String jsonData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper om = new ObjectMapper();
        ObjectNode response = om.createObjectNode();
        try {
            JSONObject json = new JSONObject(jsonData);
            if(ActorHandler.actors.containsKey(json.getInt("id")))
                ActorHandler.actors.remove(json.getInt("id"));
            Actor actor = objectMapper.readValue(jsonData, Actor.class);
            response.put("success", true);
            response.put("data", "actor added successfully");
            ActorHandler.actors.put(json.getInt("id"),actor);
            return response;
        }
        catch (Exception e) {
            InvalidCommand err = new InvalidCommand();
            response.put("success", false);
            response.put("data", err.getMessage());
            return response;
        }
    }

    public static Map<Integer, Actor> getActors() {
        return actors;
    }
}
