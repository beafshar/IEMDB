package Error;

public class ActorNotFound implements Error {
    @Override
    public String message() {
        return "ActorNotFound";
    }
}

