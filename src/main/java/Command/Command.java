package Command;

import org.json.JSONObject;

public interface Command {

    JSONObject execute(String jsonData);
}
