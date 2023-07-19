package honda.bookworm.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import honda.bookworm.tests.Business.AccessBooksIT;
import honda.bookworm.tests.Business.AccessUsersIT;
import honda.bookworm.tests.Business.CommentManagerIT;
import honda.bookworm.tests.Business.FullIT;
import honda.bookworm.tests.Business.SearchManagerIT;
import honda.bookworm.tests.Business.UserPreferenceIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessBooksIT.class,
        AccessUsersIT.class,
        SearchManagerIT.class,
        UserPreferenceIT.class,
        CommentManagerIT.class,
        FullIT.class
})

public class AllIntegrationTests {

}
