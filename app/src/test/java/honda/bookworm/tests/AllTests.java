package honda.bookworm.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import honda.bookworm.tests.Business.AccessBooksTest;
import honda.bookworm.tests.Data.BookPersistenceStubTest;
import honda.bookworm.tests.Data.UserPersistenceStubTest;
import honda.bookworm.tests.Object.BookTest;
import honda.bookworm.tests.Object.UserTest;
import honda.bookworm.tests.Object.AuthorTest;
import honda.bookworm.tests.Business.AccessUsersTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
                BookTest.class,
                UserTest.class,
                AuthorTest.class,
                UserPersistenceStubTest.class,
                BookPersistenceStubTest.class,
                AccessBooksTest.class,
                AccessUsersTest.class
})

public class AllTests {

}