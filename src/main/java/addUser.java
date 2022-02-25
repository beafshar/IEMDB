import Command.Command;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import Error.*;

public class addUser implements Command {
    @Override
    public JSONObject execute(String jsonData) throws JSONException {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject response = new JSONObject();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            User user = objectMapper.readValue(jsonData, User.class);
            UserHandler.users.add(user);
            response.put("success", true);
            response.put("data", "user added successfully");
            return response;
        }
        catch (Exception e) {
            InvalidCommand err = new InvalidCommand();
            response.put("success", false);
            response.put("data", err.message());
            return response;
        }
    }
}
