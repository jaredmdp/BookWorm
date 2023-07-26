package honda.bookworm.SystemTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import honda.bookworm.SystemTests.TestClasses.AddBooksSys;
import honda.bookworm.SystemTests.TestClasses.BookDetailsSys;
import honda.bookworm.SystemTests.TestClasses.BookImageSys;
import honda.bookworm.SystemTests.TestClasses.CreateProfileSys;
import honda.bookworm.SystemTests.TestClasses.FavBookSys;
import honda.bookworm.SystemTests.TestClasses.OpenProfileSys;
import honda.bookworm.SystemTests.TestClasses.RecommendBooksSys;
import honda.bookworm.SystemTests.TestClasses.SearchBooksSys;
import honda.bookworm.SystemTests.TestClasses.ViewBooksSys;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreateProfileSys.class,
        BookDetailsSys.class,
        FavBookSys.class,
        SearchBooksSys.class,
        ViewBooksSys.class,
        AddBooksSys.class,
        OpenProfileSys.class,
        BookImageSys.class,
        RecommendBooksSys.class
})

public class AllAcceptanceTests {
}
