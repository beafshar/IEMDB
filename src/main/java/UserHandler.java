import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import Error.*;

public class UserHandler {

    public static Set<User> users= new HashSet<User>();

    public String addUser(String jsonData) {
        addUser AU = new addUser();
        return AU.execute(jsonData);
    }

    public static String addToWatchList(String jsonData) throws JSONException {
        JSONObject json = new JSONObject(jsonData);
        String userEmail = json.getString("userEmail");
        Integer movieId = json.getInt("movieId");
        for(User user : users) {
            if(user.getEmail().equals(userEmail)) {
                return user.addToWatchList(movieId);
            }
        }
        UserNotFound err = new UserNotFound();
        return "{\"success\": false, \"data\": " + err.message() + "\"}";
    }

    public static String removeFromWatchList(String jsonData) throws JSONException {
        JSONObject json = new JSONObject(jsonData);
        String userEmail = json.getString("userEmail");
        Integer movieId = json.getInt("movieId");
        for(User user : users) {
            if(user.getEmail().equals(userEmail)) {
                return user.removeFromWatchlist(movieId);
            }
        }
        UserNotFound err = new UserNotFound();
        return "{\"success\": false, \"data\": " + err.message() + "\"}";
    }

    public static String getWatchList(String jsonData) throws JSONException {
        JSONObject json = new JSONObject(jsonData);
        String userEmail = json.getString("userEmail");
        for(User user : users) {
            if(user.getEmail().equals(userEmail)) {
                return user.getWatchList();
            }
        }
        UserNotFound err = new UserNotFound();
        return "{\"success\": false, \"data\": " + err.message() + "\"}";
    }
}
