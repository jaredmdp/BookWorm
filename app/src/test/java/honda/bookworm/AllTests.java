package honda.bookworm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import honda.bookworm.Data.UserPersistence;
import honda.bookworm.Data.UserPersistenceStubTest;
import honda.bookworm.Object.BookTest;
import honda.bookworm.Object.UserTest;
import honda.bookworm.Object.AuthorTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BookTest.class,
        UserTest.class,
        AuthorTest.class,
        UserPersistenceStubTest.class

})

public class AllTests
{

}