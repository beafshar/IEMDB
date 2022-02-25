import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import Error.*;

public class UserHandler {

    public static Set<User> users= new HashSet<User>();

    public JSONObject addUser(String jsonData) throws JSONException {
        addUser AU = new addUser();
        return AU.execute(jsonData);
    }

    public static JSONObject addToWatchList(String jsonData) throws JSONException {
        JSONObject json = new JSONObject(jsonData);
        JSONObject response = new JSONObject();
        try {
            String userEmail = json.getString("userEmail");
            Integer movieId = json.getInt("movieId");
            for (User user : users) {
                if (user.getEmail().equals(userEmail)) {
                    return user.addToWatchList(movieId);
                }
            }
            UserNotFound err = new UserNotFound();

            response.put("success", false);
            response.put("data", err.message());
            return response;
        }
        catch (JSONException e) {
            InvalidCommand err = new InvalidCommand();
            response.put("success", false);
            response.put("data", err.message());
            return response;
        }
    }

    public static JSONObject removeFromWatchList(String jsonData) throws JSONException {
        JSONObject json = new JSONObject(jsonData);
        JSONObject response = new JSONObject();
        try {
            String userEmail = json.getString("userEmail");
            Integer movieId = json.getInt("movieId");
            for (User user : users) {
                if (user.getEmail().equals(userEmail)) {
                    return user.removeFromWatchlist(movieId);
                }
            }
            UserNotFound err = new UserNotFound();
            response.put("success", false);
            response.put("data", err.message());
            return response;
        }
        catch (JSONException e) {
            InvalidCommand err = new InvalidCommand();
            response.put("success", false);
            response.put("data", err.message());
            return response;
        }
    }

    public static JSONObject getWatchList(String jsonData) throws JSONException {
        JSONObject json = new JSONObject(jsonData);
        JSONObject response = new JSONObject();
        try {
            String userEmail = json.getString("userEmail");
            for (User user : users) {
                if (user.getEmail().equals(userEmail)) {
                    return user.getWatchList();
                }
            }
            UserNotFound err = new UserNotFound();
            response.put("success", false);
            response.put("data", err.message());
            return response;
        }
        catch (JSONException e) {
            InvalidCommand err = new InvalidCommand();
            response.put("success", false);
            response.put("data", err.message());
            return response;
        }
    }
}
