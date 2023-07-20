package honda.bookworm.tests.Business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
import honda.bookworm.Business.Exceptions.InvalidSearchException;
import honda.bookworm.Business.ISearchManager;
import honda.bookworm.Business.Managers.SearchManager;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;


public class SearchManagerTest {
    private ISearchManager accessSearch;
    private IBookPersistence bookPersistence;
    private IUserPersistence userPersistence;
    @Before
    public void setup() {
        System.out.println("\nStarting test for AccessBooks");
        bookPersistence = mock(IBookPersistence.class);
        userPersistence = mock(IUserPersistence.class);
        accessSearch = new SearchManager(bookPersistence, userPersistence);
    }

    @Test
    public void testGetBooksGenreFound() {
        System.out.println("\nStarting testGetBooksGenreFound");
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book("The Way of Kings", "Brandon Sanderson", 0, Genre.Fantasy, "9780765326355"));
        mockBooks.add(new Book("Mistborn", "Brandon Sanderson", 0, Genre.Fantasy, "9780765350381"));
        mockBooks.add(new Book("Words of Radiance", "Brandon Sanderson", 0, Genre.Fantasy, "9780765326362"));

        when(bookPersistence.getBooksByGenre(Genre.Fantasy)).thenReturn(mockBooks);

        List<Book> books = accessSearch.performSearchGenre("Fantasy");
        assertEquals(books.size(), 3);

        verify(bookPersistence).getBooksByGenre(Genre.Fantasy);

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
    public void testValidateGenre_InvalidGenre() {
        System.out.println("\nStarting testValidateGenre_InvalidGenre");

        String invalidGenre = "Fiction45";
        assertThrows(InvalidSearchException.class, () -> accessSearch.performSearchGenre(invalidGenre));

        System.out.println("\nFinished testValidateGenre_InvalidGenre");
    }

    @Test
    public void testPerformSearchAuthor() throws InvalidSearchException {
        System.out.println("\nStarting testPerformSearchAuthor");
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(new Book("The Way of Kings", "Brandon Sanderson", 0, Genre.Fantasy, "9780765326355"));
        mockBooks.add(new Book("Mistborn", "Brandon Sanderson", 0, Genre.Fantasy, "9780765350381"));
        mockBooks.add(new Book("Words of Radiance", "Brandon Sanderson", 0, Genre.Fantasy, "9780765326362"));

        String query = "Brandon Sanderson";

        when(bookPersistence.getBooksByAuthor(query)).thenReturn(mockBooks);

        List<Book> result = accessSearch.performSearchAuthor(query);

        assertEquals(3, result.size());

        verify(bookPersistence).getBooksByAuthor(query);

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
    public void testPerformSearchAuthor_InvalidAuthorSymbols() {
        System.out.println("\nStarting testPerformSearchAuthor_InvalidAuthor");

        String query = "randomauth$#@ordoesntexist";
        assertThrows(InvalidSearchException.class, () -> accessSearch.performSearchAuthor(query));

        System.out.println("\nFinished testPerformSearchAuthor_InvalidAuthor");
    }

    @Test
    public void testSearchBooksByISBN() {
        System.out.println("\nStarting testSearchBooksByISBN");

        String query = "9780765326355";

        Book sampleBook = new Book("The Way of Kings", "Brandon Sanderson", 0, Genre.Fantasy, "9780765326355",
                "From #1 New York Times bestselling author Brandon Sanderson, The Way of Kings, book one of The Stormlight Archive begins an incredible new saga of epic proportion.\n" +
                        "The Knights Radiant must stand again.");

        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(sampleBook);

        when(bookPersistence.searchBooksByISBN(query)).thenReturn(mockBooks);

        List<Book> result = accessSearch.performSearchISBN(query);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleBook, result.get(0));

        verify(bookPersistence).searchBooksByISBN(query);

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
                        "The Knights Radiant must stand again.");

        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(sampleBook);

        when(bookPersistence.getBooksByTitle(query)).thenReturn(mockBooks);

        List<Book> result = accessSearch.performSearchTitle(query);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(sampleBook, result.get(0));

        verify(bookPersistence).getBooksByTitle(query);

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

    @Test
    public void testPerformSearchUser_InvalidUser() {
        System.out.println("\nStarting testPerformSearchUser_InvalidUser");

        String query = "randomauthordoesntexist";
        List<User> userList = accessSearch.performSearchUser(query);
        assertTrue(userList.isEmpty());

        when(userPersistence.searchUsersByQuery("gen")).thenThrow(GeneralPersistenceException.class);
        assertThrows(InvalidSearchException.class, () -> accessSearch.performSearchUser("gen"));

        System.out.println("\nFinished testPerformSearchUser_InvalidUser");
    }

    @Test
    public void testPerformSearchUser_InvalidUserSymbols() {
        System.out.println("\nStarting testPerformSearchUser_InvalidUserSymbols");

        String query = "ran.mauth$#@ordoesnt : ; $%";
        assertThrows(InvalidSearchException.class, () -> accessSearch.performSearchUser(query));

        System.out.println("\nFinished testPerformSearchUser_InvalidUserSymbols");
    }
    
}
