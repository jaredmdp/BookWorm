package honda.bookworm.tests.Business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

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
    private final String testISBN = "9780044403371";
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
    public void testAddComment() {
        System.out.println("Starting testAddComment");

        Comment expected = new Comment(testUser, testISBN, "Test");

        Comment result = commentManager.leaveComment(testISBN, "Test");

        assert(expected.getUsername() == result.getUsername());
        assert(expected.getISBN() == result.getISBN());
        assert(expected.getComment() == result.getComment());

        Timestamp resultTime = Timestamp.valueOf(result.getTime());
        Timestamp expectedCloseTo = new Timestamp(System.currentTimeMillis());

        assert(expectedCloseTo.compareTo(resultTime) <= 1000 && expectedCloseTo.compareTo(resultTime) >=0);

        System.out.println("Finished testAddComment");
    }

    @After
    public void tearDown(){
        Services.setActiveUser(null);
        this.tempDB.delete();
    }
}
