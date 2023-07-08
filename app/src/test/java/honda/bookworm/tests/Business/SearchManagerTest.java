package honda.bookworm.tests.Business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import honda.bookworm.Business.Exceptions.InvalidSearchException;
import honda.bookworm.Business.ISearchManager;
import honda.bookworm.Business.Managers.SearchManager;
import honda.bookworm.Data.Stubs.BookPersistenceStub;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class SearchManagerTest {

    private ISearchManager accessSearch;

    @Before
    public void setup() {
        System.out.println("\nStarting test for AccessBooks");
        accessSearch = new SearchManager(new BookPersistenceStub());
    }

    @Test
    public void testGetBooksGenreFound() {
        System.out.println("\nStarting testGetBooksGenreFound");

        List<Book> books = accessSearch.performSearchGenre("Fantasy");
        assertEquals(books.size(), 5);
        System.out.println("\nFinished testGetBooksGenreFound");

    }

    @Test
    public void testGetBooksGenreNotFound() {
        System.out.println("\nStarting testGetBooksGenreNotFound");
        List<Book> newList;

        newList = accessSearch.performSearchGenre("CrzyWackoGenreLol");
        assertTrue(newList.isEmpty());

        System.out.println("\nFinished testGetBooksGenreNotFound");

    }

    @Test
    public void testPerformSearchGenre() throws InvalidSearchException {
        System.out.println("\nStarting testPerformSearchGenre");

        // Test case 1: Valid genre
        String query1 = "Fiction";
        List<Book> result1 = accessSearch.performSearchGenre(query1);
        assertNotNull(result1);
        assertFalse(result1.isEmpty());

        // Test case 2: Invalid genre
        String query2 = "InvalidGenre";
        List<Book> result2 = accessSearch.performSearchGenre(query2);
        assertTrue(result2.isEmpty());

        System.out.println("\nFinished testPerformSearchGenre");
    }

    @Test
    public void testValidateGenre_InvalidGenre() {
        System.out.println("\nStarting testValidateGenre_InvalidGenre");

        String invalidGenre = "Fiction45";
        assertThrows(InvalidSearchException.class, () -> accessSearch.performSearchGenre(invalidGenre));

        System.out.println("\nFinished testValidateGenre_InvalidGenre");
    }

    @Test
    public void testPerformSearchAuthor() throws InvalidSearchException {
        System.out.println("\nStarting testPerformSearchAuthor");

        String query = "Brandon Sanderson";

        List<Book> result = accessSearch.performSearchAuthor(query);

        assertEquals(5, result.size());
        System.out.println("\nFinished testPerformSearchAuthor");
    }

    @Test
    public void testPerformSearchAuthor_InvalidAuthor() {
        System.out.println("\nStarting testPerformSearchAuthor_InvalidAuthor");

        String query = "randomauthordoesntexist";
        List<Book> booklist = accessSearch.performSearchAuthor(query);
        assertTrue(booklist.isEmpty());

        System.out.println("\nFinished testPerformSearchAuthor_InvalidAuthor");
    }

    @Test
    public void testSearchBooksByISBN() {
        System.out.println("\nStarting testSearchBooksByISBN");

        Book sampleBook = new Book("The Way of Kings", "Brandon Sanderson", 0, Genre.Fantasy, "9780765326355",
                "From #1 New York Times bestselling author Brandon Sanderson, The Way of Kings, book one of The Stormlight Archive begins an incredible new saga of epic proportion.\n" +
                        // ... book description ...
                        "The Knights Radiant must stand again.");
        // Test case 1: Valid ISBN
        String query1 = "9780765326355";
        List<Book> result1 = accessSearch.performSearchISBN(query1);
        assertNotNull(result1);
        assertEquals(1, result1.size());
        assertEquals(sampleBook, result1.get(0));

        System.out.println("\nFinished testSearchBooksByISBN");
    }

    @Test
    public void testSearchBooksByISBN_InvalidISBN() {
        System.out.println("\nStarting testSearchBooksByISBN_InvalidISBN");

        String query2 = "123abc67890";
        List<Book> result2 = new ArrayList<>();
        try {
            result2 = accessSearch.performSearchISBN(query2);
            fail("Expected InvalidSearchException to be thrown");
        } catch (InvalidSearchException e) {
            assertNotNull(e.getMessage());
        }

        assertTrue(result2.isEmpty());

        System.out.println("\nFinished testSearchBooksByISBN_InvalidISBN");
    }

    @Test
    public void testPerformSearchTitle_ValidTitle() {
        System.out.println("\nStarting testPerformSearchTitle_ValidTitle");

        String query = "The Way of Kings";

        Book sampleBook = new Book("The Way of Kings", "Brandon Sanderson", 0, Genre.Fantasy, "9780765326355",
                "From #1 New York Times bestselling author Brandon Sanderson, The Way of Kings, book one of The Stormlight Archive begins an incredible new saga of epic proportion.\n" +
                        // ... book description ...
                        "The Knights Radiant must stand again.");

        List<Book> result = accessSearch.performSearchTitle(query);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleBook, result.get(0));

        System.out.println("\nFinished testPerformSearchTitle_ValidTitle");
    }



    @Test
    public void testPerformSearchTitle_InvalidTitle() {
        System.out.println("\nStarting testPerformSearchTitle_InvalidTitle");

        String query = "The Way of Queens";

        List<Book> result = accessSearch.performSearchTitle(query);

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());

        System.out.println("\nFinished testPerformSearchTitle_InvalidTitle");


    }
    
}
