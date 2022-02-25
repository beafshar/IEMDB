package Error;

public class UserNotFound implements Error {
    @Override
    public String message() {
        return "UserNotFound";
    }
}
