package honda.bookworm.tests.Business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.ICommentManager;
import honda.bookworm.Business.Managers.AccessUsers;
import honda.bookworm.Business.Managers.CommentManager;
import honda.bookworm.Data.ICommentPersistence;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Data.hsqldb.CommentPersistenceHSQLDB;
import honda.bookworm.Data.hsqldb.UserPersistenceHSQLDB;
import honda.bookworm.Object.Comment;
import honda.bookworm.tests.utils.TestUtils;

public class CommentManagerIT {

    private ICommentManager commentManager;
    private File tempDB;
    private final String testUser = "TestUser";

    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final ICommentPersistence persistenceComment = new CommentPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.commentManager = new CommentManager(persistenceComment);

        final IUserPersistence persistenceUser = new UserPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        AccessUsers addTestUser = new AccessUsers(persistenceUser);
        addTestUser.addNewUser("First", "Last", testUser, "Password", false);
    }

    @Test
    public void testLeaveComment() {
        System.out.println("Starting testAddComment");

        String testISBN = "9780199219761";

        long startTime = System.currentTimeMillis();

        Comment result = commentManager.leaveComment(testISBN, "Test");
        Comment expected = commentManager.getCommentsOnBook(testISBN).get(0);

        assert(expected.getUsername().equals(result.getUsername()));
        assert(expected.getISBN().equals(result.getISBN()));
        assert(expected.getComment().equals(result.getComment()));
        assert(expected.getTime() == result.getTime());

        //should have been inserted in a reasonable amount of time
        long timeDiff = expected.getTime() - startTime;
        assert(timeDiff >= 0 && timeDiff <= 1000);

        System.out.println("Finished testAddComment");
    }

    @Test
    public void testGetCommentsOnBook(){
        System.out.println("Starting testGetCommentsOnBook");

        String bookISBN = "9780044403371";

        List<Comment> comments = commentManager.getCommentsOnBook(bookISBN);

        for(int i=0; i<comments.size(); i++){
            assert(comments.get(i).getISBN().equals(bookISBN));
            assert(comments.get(i).getUsername() != null);
            assert(comments.get(i).getComment() != null);

            if(i <comments.size() - 1){
                assert(comments.get(i).getTime() > comments.get(i+1).getTime());
            }
        }

        System.out.println("Finished testGetCommentsOnBook");
    }

    @After
    public void tearDown(){
        Services.setActiveUser(null);
        this.tempDB.delete();
    }
}
