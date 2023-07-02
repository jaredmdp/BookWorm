package honda.bookworm.tests.Business;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Data.Stubs.BookPersistenceStub;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Business.Exceptions.*;

public class AccessBooksTest {
    private AccessBooks accessBooks;

    @Before
    public void setup() {
        System.out.println("\nStarting test for AccessBooks");
        accessBooks = new AccessBooks(new BookPersistenceStub());
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
        } catch (InvalidGenreException e) {
            caught = true;
        }

        assertTrue(caught);

        System.out.println("\nFinished testGetBooksGenreNotFound");

    }

    @Test
    public void testGetTrimmedBookTitles(){
        Book testBook = new Book("Super Dooper Ginormous Fungus Amoung Us Volume Three","", 0, Genre.Fiction, "123123");
        String trimmed;

        System.out.println("\nTesting getTrimmedBookTitles");
        assertTrue(testBook.getName().length()>40);

        trimmed = accessBooks.getTrimmedBookName(testBook);
        assertTrue("Trimmed title must be shorter than actual book title", testBook.getName().length()>trimmed.length() );
        assertTrue(trimmed.contains("Super") && trimmed.contains("Three"));
        assertFalse(trimmed.contains("Amoung Us Volume"));

        testBook.setName("Superdooperlongwordthathasmorethan40character"); //huge one word title
        trimmed = accessBooks.getTrimmedBookName(testBook);
        assertTrue("Trimmed title must be shorter than actual book title", testBook.getName().length()>trimmed.length() );

        testBook.setName("Not the longest word supercalifragilisticexpialidocious");
        trimmed = accessBooks.getTrimmedBookName(testBook);
        assertFalse(trimmed.contains("supercalifragilisticexpialidocious"));
        assertTrue("Trimmed title must be shorter than actual book title", testBook.getName().length()>trimmed.length() );

        testBook.setName("A short title that is nice");
        trimmed = accessBooks.getTrimmedBookName(testBook);
        assertTrue("Trimmed title must be equal to actual book title", testBook.getName().length()==trimmed.length() );

        System.out.println("\nFinished getTrimmedBookTitles Test");
    }

}
