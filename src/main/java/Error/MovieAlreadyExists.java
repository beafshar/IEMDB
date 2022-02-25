package Error;

public class MovieAlreadyExists implements Error {
    @Override
    public String message() {
        return "MovieAlreadyExists";
    }
}
