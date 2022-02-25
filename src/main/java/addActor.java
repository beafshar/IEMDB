
import Command.Command;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

public class addActor {
    public JSONObject execute(String jsonData) throws JSONException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSONObject response = new JSONObject();
        response.put("success", true);
        try {
            int up = 0;
            Actor actor = null;
            JSONObject json = new JSONObject(jsonData);
            for(Actor a : ActorHandler.actors) {
                if(a.getId() == json.getInt("id")) {
                    ActorHandler.actors.remove(a);
                    up = 1;
                    a.updateActor(json.getInt("id"), json.getString("name"), json.getString("birthday"), json.getString("nationality"));
                    actor = a;
                    response.put("data", "actor updated successfully");
                }
            }

            if(up == 0) {
                actor = objectMapper.readValue(jsonData, Actor.class);
                response.put("data", "actor added successfully");
            }
            ActorHandler.actors.add(actor);
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
