package honda.bookworm.tests.Business;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.InvalidCommentException;
import honda.bookworm.Business.Exceptions.Users.UserException;
import honda.bookworm.Business.ICommentManager;
import honda.bookworm.Business.Managers.CommentManager;
import honda.bookworm.Data.ICommentPersistence;
import honda.bookworm.Object.Comment;
import honda.bookworm.Object.User;

public class CommentManagerTest {

    private ICommentManager commentManager;
    private ICommentPersistence commentPersistence;
    private User loggedIn;
    private final String testISBN = "1111111111111";

    @Before
    public void setup(){
        System.out.println("Starting for CommentManager");

        commentPersistence = mock(ICommentPersistence.class);

        commentManager = new CommentManager(commentPersistence);

        loggedIn = new User("First", "Last", "User", "Pass");

        Services.setActiveUser(loggedIn);
    }

    @Test
    public void testLeaveCommentSuccess(){
        System.out.println("Starting testLeaveCommentSuccess");

        Comment expected = new Comment(loggedIn.getUsername(), testISBN, "Test");

        when(commentPersistence.addComment(expected)).thenReturn(expected);
        commentManager.leaveComment(testISBN, "Test");

        verify(commentPersistence).addComment(expected);
        verify(commentPersistence, times(1)).addComment(expected);

        System.out.println("Finished testLeaveCommentSuccess");
    }

    @Test
    public void testLeaveCommentFailure(){
        System.out.println("Starting testLeaveCommentFailure");

        String tooLongBase = "11111111111111111111111111111111111111111111111111";
        final String tooLong = tooLongBase + tooLongBase +tooLongBase +tooLongBase +tooLongBase +tooLongBase +tooLongBase +tooLongBase +tooLongBase +tooLongBase +tooLongBase +tooLongBase;

        assertThrows("Comment must be at least 1 characters long", InvalidCommentException.class, ()-> commentManager.leaveComment(testISBN, ""));
        assertThrows("Comment cannot exceed 280 characters", InvalidCommentException.class, ()-> commentManager.leaveComment(testISBN, "" + tooLong));

        Services.setActiveUser(null);
        assertThrows("Please log in to leave a comment", UserException.class, ()-> commentManager.leaveComment(testISBN, "Test"));

        verify(commentPersistence, times(0)).addComment(any(Comment.class));

        System.out.println("Finished testLeaveCommentSuccess");
    }
}
