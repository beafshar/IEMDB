import Command.Command;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class addUser implements Command {
    @Override
    public JSONObject execute(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            User user = objectMapper.readValue(jsonData, User.class);
            UserHandler.users.add(user);
            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("data", "user added successfully");
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
