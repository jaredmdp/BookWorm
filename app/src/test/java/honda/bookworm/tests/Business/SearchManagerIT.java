package honda.bookworm.tests.Business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Business.Exceptions.InvalidSearchException;
import honda.bookworm.Business.ISearchManager;
import honda.bookworm.Business.Managers.SearchManager;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Data.hsqldb.BookPersistenceHSQLDB;
import honda.bookworm.Data.hsqldb.UserPersistenceHSQLDB;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.User;
import honda.bookworm.tests.utils.TestUtils;

public class SearchManagerIT {
    private ISearchManager accessSearch;
    private File tempDB;

    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final IBookPersistence persistence = new BookPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IUserPersistence uPersistence = new UserPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.accessSearch = new SearchManager(persistence,uPersistence);
    }

    @Test
    public void testPerformSearchAuthor() throws InvalidSearchException {
        String query = "Jane Austen";

        List<Book> result = accessSearch.performSearchAuthor(query);

        assertEquals(5, result.size());
    }

    @Test
    public void testPerformSearchFirstNameAuthor() throws InvalidSearchException {
        String query = "Jane";

        List<Book> result = accessSearch.performSearchAuthor(query);

        assertEquals(5, result.size());
    }

    @Test
    public void testPerformSearchLastNameAuthor() throws InvalidSearchException {
        String query = "Austen";

        List<Book> result = accessSearch.performSearchAuthor(query);

        assertEquals(5, result.size());
    }

    @Test
    public void testPerformSearchAuthor_InvalidAuthor() {
        String query = "Brandon Sanderson";
        List<Book> booklist = accessSearch.performSearchAuthor(query);
        assertTrue(booklist.isEmpty());
    }

    @Test
    public void testPerformSearchISBN() {
        String query = "9780007123803"; //return of the King book

        List<Book> result = null;
        result = accessSearch.performSearchISBN(query);

        assert result != null;
        assertEquals(1, result.size());
    }

    @Test
    public void testPerformSearchISBN_InvalidISBN() {
        String query = "123456"; //return of the King book

        List<Book> result = new ArrayList<>();
        try {
            result = accessSearch.performSearchISBN(query);
        } catch (InvalidSearchException e) {
            assert(false);//a wrong search should return nothing, not die
        }

        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetBooksGenreFound(){
        System.out.println("\nStarting testGetBooksGenreFound");

        List<Book> books = accessSearch.performSearchGenre("Fantasy");
        assertEquals(5, books.size());

        System.out.println("\nFinished testGetBooksGenreFound");
    }

    @Test
    public void testGetBooksGenreFoundWithDash(){
        System.out.println("\nStarting testGetBooksGenreFoundWithDash");

        List<Book> books = accessSearch.performSearchGenre("Sci-Fi");
        assertEquals(2, books.size());

        System.out.println("\nFinished testGetBooksGenreFoundWithDash");
    }

    @Test
    public void testPerformSearchTitle_ValidTitle() throws InvalidSearchException {
        String query = "Harry Potter";

        List<Book> result = accessSearch.performSearchTitle(query);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        for (Book book : result) {
            assertTrue(book.getName().toLowerCase().contains("harry potter"));
        }
    }

    @Test
    public void testPerformSearchTitle_InvalidTitle() {
        String query = "Harry Pawter: Alternative life where hes just a muggle lol";
        List<Book> newList = accessSearch.performSearchTitle(query);
        assertTrue(newList.isEmpty());
    }

    @Test
    public void testPerformSearchUser_PartialQuery() throws InvalidSearchException {
        String query = "en";

        List<User> result = accessSearch.performSearchUser(query);

        assertEquals(4, result.size());
    }

    @Test
    public void testPerformSearchUser() throws InvalidSearchException {
        String query = "Jane Austen";

        List<User> result = accessSearch.performSearchUser(query);

        assertEquals(1, result.size());
    }

    @Test
    public void testPerformSearchFirstNameUser() throws InvalidSearchException {
        String query = "Jane";

        List<User> result = accessSearch.performSearchUser(query);

        assertEquals(2, result.size());
    }

    @Test
    public void testPerformSearchLastNameUser() throws InvalidSearchException {
        String query = "Austen";

        List<User> result = accessSearch.performSearchUser(query);

        assertEquals(1, result.size());
    }

    @Test
    public void testPerformSearchUser_InvalidUser() {
        String query = "SomeRandom User";
        List<User> result = accessSearch.performSearchUser(query);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testPerformSearchUser_InvalidQuery() {
        String query = "SomeRandom User.*&";
        try {
            accessSearch.performSearchAuthor(query);
            fail("This line should not run");
        }catch (Exception e){
            assert (e instanceof  InvalidSearchException);
        }
    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }
}
