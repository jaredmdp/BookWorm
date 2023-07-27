package honda.bookworm.tests.Object;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import honda.bookworm.Object.Comment;

public class CommentTest {

    @Test
    public void testEquals() {
        System.out.println("\nStarting Equals");

        Comment base = new Comment("User", "1111111111111", "Comment");

        Comment correct = new Comment("User", "1111111111111", "Comment");
        correct.setTime(base.getTime());

        Comment wrongUser = new Comment("User2", "1111111111111", "Comment");
        wrongUser.setTime(base.getTime());

        Comment wrongISBN = new Comment("User", "1111111111112", "Comment");
        wrongISBN.setTime(base.getTime());

        Comment wrongComment = new Comment("User", "1111111111111", "Comment2");
        wrongComment.setTime(base.getTime());

        Comment wrongTime = new Comment("User", "1111111111111", "Comment");

        assertTrue(base.equals(correct));
        assertFalse(base.equals(wrongUser));
        assertFalse(base.equals(wrongISBN));
        assertFalse(base.equals(wrongComment));
        assertFalse(base.equals(wrongTime));

        System.out.println("\nFinished Equals");
    }
}
