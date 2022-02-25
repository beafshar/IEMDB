package Error;

public class InvalidVoteValue implements Error {
    @Override
    public String message() {
        return "InvalidVoteValue";
    }
}

