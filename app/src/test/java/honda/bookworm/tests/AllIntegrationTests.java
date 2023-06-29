package honda.bookworm.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import honda.bookworm.tests.Business.AccessBooksIT;
import honda.bookworm.tests.Business.AccessUsersIT;
import honda.bookworm.tests.Business.FullIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessBooksIT.class,
        AccessUsersIT.class,
        FullIT.class
})

public class AllIntegrationTests {

}
