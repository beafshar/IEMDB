package Error;

public class AgeLimitError implements Error {
    @Override
    public String message() {
        return "AgeLimitError";
    }
}
