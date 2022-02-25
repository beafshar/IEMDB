package Error;

public class InvalidCommand implements Error {
    @Override
    public String message() {
        return "InvalidCommand";
    }
}
