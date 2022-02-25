
import Command.Command;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class addActor {
    public JSONObject execute(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Actor actor = objectMapper.readValue(jsonData, Actor.class);
            ActorHandler.actors.add(actor);
            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("data", "actor added successfully");
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
