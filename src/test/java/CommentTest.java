import Model.Comment;
import Model.User;
import Model.UserHandler;
import org.junit.After;
import org.junit.Before;

public class CommentTest {
    UserHandler users;
    User user;
    Comment comment;

    @Before
    public void setup()
    {
        user = new User("Mahdi@gmail.com","1234", "mahdi", "mahdi mahdavi","2000-02-01");
        users = new UserHandler();
//        comment = new Model.Comment();
    }

    @After
    public void teardown()
    {
        user = null;
        comment = null;
    }

//    @Test
//    public void userSuccessfullyVotesToAComment() throws Exception {
//        int likes = comment.getLikes();
//        int dislikes = comment.getDislikes();
//        ObjectNode response = comment.addVote(user.getEmail(), 1);
//        //assert response
//        assertEquals(likes + 1, comment.getLikes());
//        assertEquals(dislikes, comment.getDislikes());
//    }
//
//    @Test
//    public void userVotesACommentTwice() throws Exception {
//        int likes = comment.getLikes();
//        int dislikes = comment.getDislikes();
//        ObjectNode response = comment.addVote(user.getEmail(), 1);
//        response = comment.addVote(user.getEmail(), -1);
//        //assert response
//        assertEquals(likes, comment.getLikes());
//        assertEquals(dislikes + 1, comment.getDislikes());
//    }


//
//    @Test
//    public void InvalidVoteScore() throws Exception {
//        int likes = comment.getLikes();
//        int dislikes = comment.getDislikes();
//        ObjectNode response = comment.addVote(user.getEmail(), 7);
//        //assert response
//        assertEquals(likes, comment.getLikes());
//        assertEquals(dislikes, comment.getDislikes());
//    }
}
