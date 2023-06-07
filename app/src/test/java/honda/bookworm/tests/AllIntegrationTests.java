package honda.bookworm.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import honda.bookworm.tests.Business.AccessBooksTest;
import honda.bookworm.tests.Business.AccessUsersTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessBooksTest.class,
        AccessUsersTest.class
})

public class AllIntegrationTests {

}