import Command.Command;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class addUser implements Command {
    @Override
    public String execute(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            User user = objectMapper.readValue(jsonData, User.class);
            System.out.println(user.getName());
            System.out.println(user.getEmail());

            Handler.Users.add(user);

            return "{\"success\":true \"data\": \"user added successfully\"}";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
