import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import Error.*;

public class UserHandler {

    public static Map<String, User> users = new HashMap<>();

    public void setUsers(User[] users) {
        for (User user : users)
            this.users.put(user.getEmail(), user);
    }

    public ObjectNode addUser(String jsonData) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper om = new ObjectMapper();
        ObjectNode response = om.createObjectNode();
        JSONObject json = new JSONObject(jsonData);
        User user = objectMapper.readValue(jsonData, User.class);
        users.put(json.getString("email"), user);
        response.put("success", true);
        response.put("data", "user added successfully");
        return response;
    }

    public static User findUser(String email) {
        return users.get(email);
    }

    public static ObjectNode UserNotFound() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        UserNotFound err = new UserNotFound();
        response.put("success", false);
        response.put("data", err.getMessage());
        return response;
    }

    public ObjectNode addToWatchList(String jsonData) throws Exception {
        JSONObject json = new JSONObject(jsonData);
        String userEmail = json.getString("userEmail");
        int movieId = json.getInt("movieId");
        if(users.containsKey(userEmail))
            return users.get(userEmail).addToWatchList(movieId);
        return UserNotFound();
    }

    public static ObjectNode removeFromWatchList(String jsonData) throws Exception {
        JSONObject json = new JSONObject(jsonData);
        String userEmail = json.getString("userEmail");
        int movieId = json.getInt("movieId");
        if(users.containsKey(userEmail))
            return users.get(userEmail).removeFromWatchList(movieId);
        return UserNotFound();
    }

    public static ObjectNode getWatchList(String jsonData) throws Exception {
        JSONObject json = new JSONObject(jsonData);
        String userEmail = json.getString("userEmail");
        if (users.containsKey(userEmail))
            return users.get(userEmail).getWatchList();
        return UserNotFound();
    }
}
