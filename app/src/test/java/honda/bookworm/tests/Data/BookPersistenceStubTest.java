package honda.bookworm.tests.Data;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Data.BookPersistence;
import honda.bookworm.Data.Stubs.BookPersistenceStub;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

public class BookPersistenceStubTest {
    private BookPersistenceStub bookStub;
    @Before
    public void setup(){
        bookStub = new BookPersistenceStub();
    }

    @Test public void testGetAllBooks() {
        System.out.println("\nStarting testGetAllBooks");

        List<Book> allBooks = bookStub.getAllBooks();
        List<Book> dummyBooks = new ArrayList<>();

        dummyBooks.add(new Book("The Way of Kings", "Brandon Sanderson", Genre.Fantasy, "9780765326355"));
        dummyBooks.add(new Book("Mistborn", "Brandon Sanderson", Genre.Fantasy, "9780765350381"));
        dummyBooks.add(new Book("Words of Radiance", "Brandon Sanderson", Genre.Fantasy, "9780765326362"));
        dummyBooks.add(new Book("Elantris", "Brandon Sanderson", Genre.Fantasy, "9780765311788"));
        dummyBooks.add(new Book("The Alloy of Law", "Brandon Sanderson", Genre.Fantasy, "9780765368546"));

        assertNotNull(allBooks);
        assertEquals(5, allBooks.size());
        assertEquals(allBooks, dummyBooks);

        System.out.println("\nFinished testGetAllBooks");
    }

    @Test
    public void testGetBookByISBN() {
        System.out.println("\nStarting testGetBookByISBN");

        String ISBN = "9780765311788"; //Elantris by Brandon Sanderson
        Book book = bookStub.getBookByISBN(ISBN);
        assertNotNull(book);
        assertEquals(ISBN, book.getISBN());
        assertEquals("Elantris", book.getName());

        System.out.println("\nFinished testGetBookByISBN");
    }

    @Test
    public void testGetBookByTitle() {
        System.out.println("\nStarting testGetBookByTitle");

        String title = "Mistborn";
        Book book = bookStub.getBookByTitle(title);
        assertNotNull(book);
        assertEquals(title, book.getName());

        title = "random book that is not in Stub";
        book = bookStub.getBookByTitle(title);
        assertNull(book);

        System.out.println("\nFinished testGetBookByTitle");
    }

    @Test
    public void testAddBook() {
        System.out.println("\nStarting testAddBook");

        Book newBook = new Book("Oathbringer", "Brandon Sanderson", Genre.Fantasy, "9783453270381");
        Book result = bookStub.addBook(newBook);
        assertNotNull(result);
        assertEquals(newBook, result);

        //check if the added book is in our list
        List<Book> allBooks = bookStub.getAllBooks();
        assertTrue(allBooks.contains(newBook));

        System.out.println("\nFinished testAddBook");
    }

    @Test
    public void testRemoveBookByISBN() {
        System.out.println("\nStarting testRemoveBookByISBN");

        String ISBN = "9780765311788"; //Elantris book
        List<Book> allBooks = bookStub.getAllBooks();

        assertEquals(5, allBooks.size());
        bookStub.removeBookByISBN(ISBN);

        //update book count
        allBooks = bookStub.getAllBooks();

        Book result = bookStub.getBookByISBN(ISBN);
        assertNull(result);
        assertEquals(4, allBooks.size());

        System.out.println("\nFinished testRemoveBookByISBN");
    }

    @Test
    public void testRemoveBookByTitle() {
        System.out.println("\nStarting testRemoveBookByTitle");

        String Title = "Mistborn";
        List<Book> allBooks = bookStub.getAllBooks();

        assertEquals(5, allBooks.size());
        bookStub.removeBookByTitle(Title);

        //update book count
        allBooks = bookStub.getAllBooks();

        Book result = bookStub.getBookByTitle(Title);
        assertNull(result);
        assertEquals(4, allBooks.size());

        //test book that is not in system
        Title = "Skyward";
        allBooks = bookStub.getAllBooks();
        bookStub.removeBookByTitle(Title);

        //update book count
        allBooks = bookStub.getAllBooks();
        assertEquals(4, allBooks.size());

        System.out.println("\nFinished testRemoveBookByTitle");
    }

    //We need to rewrite this test once we create adjustment to list Authors by Objects!
    @Test
    public void testGetBooksByAuthor() {
        //test author that is not in Stub
        String author = "JK Rowling";
        List<Book> authorBooks = bookStub.getBooksByAuthor(author);
        assertNull(authorBooks);

        //test valid author
        author = "Brandon Sanderson";
        authorBooks = bookStub.getBooksByAuthor(author);
        assertNotNull(authorBooks);
        assertEquals(5, authorBooks.size());
    }

}
