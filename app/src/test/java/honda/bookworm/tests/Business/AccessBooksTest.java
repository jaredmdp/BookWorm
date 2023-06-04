package honda.bookworm.tests.Business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.NoSuchElementException;

import honda.bookworm.Business.AccessBooks;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

public class AccessBooksTest {
    private AccessBooks accessBooks;

    @Before
    public void setup() {
        System.out.println("\nStarting test for AccessBooks");
        accessBooks = new AccessBooks();
    }

    @Test
    public void testGetBooksGenreFound() {
        System.out.println("\nStarting testGetBooksGenreFound");

        List<Book> books = accessBooks.getBooksGenre(Genre.Fantasy);
        assertEquals(books.size(), 5);

        System.out.println("\nFinished testGetBooksGenreFound");

    }

    @Test
    public void testGetBooksGenreNotFound() {
        System.out.println("\nStarting testGetBooksGenreNotFound");
        boolean caught = false;

        try {
            accessBooks.getBooksGenre(null);
        } catch (NullPointerException e) {
            caught = true;
        }

        assertTrue(caught);

        System.out.println("\nFinished testGetBooksGenreNotFound");

    }

}
