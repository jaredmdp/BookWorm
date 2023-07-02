package honda.bookworm.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import honda.bookworm.tests.Business.AccessBooksTest;
import honda.bookworm.tests.Business.AccessUsersTest;
import honda.bookworm.tests.Object.BookTest;
import honda.bookworm.tests.Object.UserTest;
import honda.bookworm.tests.Object.AuthorTest;
import honda.bookworm.tests.Business.StringValidatorTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        BookTest.class,
        UserTest.class,
        AuthorTest.class,
        StringValidatorTest.class,
        AccessBooksTest.class,
        AccessUsersTest.class
})

public class AllUnitTests {

}