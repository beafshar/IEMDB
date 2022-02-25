package Command;

import org.json.JSONException;
import org.json.JSONObject;

public interface Command {

    JSONObject execute(String jsonData) throws JSONException;
}
