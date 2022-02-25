import java.util.HashSet;
import java.util.Set;

public class UserHandler {

    public static Set<User> users= new HashSet<User>();

    public String addUser(String jsonData) {
        addUser AU = new addUser();
        return AU.execute(jsonData);
    }
}
