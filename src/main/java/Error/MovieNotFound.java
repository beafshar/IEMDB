package Error;

public class MovieNotFound implements Error {
    @Override
    public String message() {
        return "MovieNotFound";
    }
}

