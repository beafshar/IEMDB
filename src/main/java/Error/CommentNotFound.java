package Error;

public class CommentNotFound implements Error {
    @Override
    public String message() {
        return "CommentNotFound";
    }
}

