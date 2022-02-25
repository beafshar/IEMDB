
import Command.Command;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
public class addActor implements Command {
    @Override
    public String execute(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Actor actor = objectMapper.readValue(jsonData, Actor.class);
            System.out.println(actor.getNationality());
            System.out.println(actor.getBirthDate());
            System.out.println(actor.getName());
            System.out.println(actor.getId());
            Handler.actors.add(actor);
            return "{\"success\": \"actor added successfully\"}";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
