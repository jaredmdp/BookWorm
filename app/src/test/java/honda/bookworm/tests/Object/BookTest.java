package honda.bookworm.tests.Object;

import org.junit.Test;

import static org.junit.Assert.*;

import honda.bookworm.Object.Book;

import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

public class BookTest {

    @Test
    public void testBookGet() {
        System.out.println("\nStarting BookGet");

        Book book = new Book("Dune", "Frank Herbert", 0, Genre.SciFi, "9780399128967");

        assertEquals("Dune", book.getName());
        assertEquals("Frank Herbert", book.getAuthor());
        assertEquals(0, book.getAuthorID());
        assertEquals(Genre.SciFi, book.getGenre());
        assertEquals("9780399128967", book.getISBN());

        System.out.println("\nFinished BookGet");
    }

    @Test
    public void testBookSet() {
        System.out.println("\nStarting BookSet");

        Book book = new Book("Dune", "Frank Herbert", 0, Genre.SciFi, "9780399128967");

        book.setName("Harry Potter and the Philosopher's Stone");
        book.setAuthor("JK Rowling");
        book.setISBN("9780747532743");
        book.setAuthorID(1);
        book.setGenre(Genre.Fiction);

        assertEquals("Harry Potter and the Philosopher's Stone", book.getName());
        assertEquals("JK Rowling", book.getAuthor());
        assertEquals(Genre.Fiction, book.getGenre());
        assertEquals("9780747532743", book.getISBN());
        assertEquals(1, book.getAuthorID());

        System.out.println("\nFinished BookSet");
    }

    @Test
    public void testEquals() {
        System.out.println("\nStarting Equals");

        Book book = new Book("Dune", "Frank Herbert", 0, Genre.SciFi, "9780399128967");
        Book rightBook = new Book("Dune", "Frank Herbert", 0, Genre.SciFi, "9780399128967");
        Book wrongTitle = new Book("Dun", "Frank Herbert", 0, Genre.SciFi, "9780399128967");
        Book wrongAuthor= new Book("Dun", "Fran Herbert", 0, Genre.SciFi, "9780399128967");
        Book wrongGenre = new Book("Dun", "Frank Herbert", 0, Genre.Romance, "9780399128967");
        Book wrongISBN = new Book("Dun", "Frank Herbert", 0, Genre.SciFi, "12");
        Book wrongAuthorID = new Book("Dune", "Frank Herbert", 1, Genre.SciFi, "9780399128967");
        User wrongObject = new User("wrong", "object", "test", "123");

        assertTrue(book.equals(rightBook));
        assertFalse(book.equals(wrongTitle));
        assertFalse(book.equals(wrongAuthor));
        assertFalse(book.equals(wrongGenre));
        assertFalse(book.equals(wrongISBN));
        assertFalse(book.equals(wrongAuthorID));
        assertFalse(book.equals(wrongObject));

        System.out.println("\nFinished Equals");
    }

    @Test
    public void testBookDescription() {
        String name = "The Way of Kings";
        String author = "Brandon Sanderson";
        Genre genre = Genre.Fantasy;
        String ISBN = "9780765326355";
        String description = "Fantasy novel with rich world-building and compelling characters.";

        Book book = new Book(name, author, 0, genre,ISBN, description);

        assertEquals(description, book.getDescription());

        name = "1984";
        author = "George Orwell";
        genre = Genre.Fiction;
        ISBN = "9780451524935";
        book = new Book(name, author, 0, genre, ISBN);

        assertEquals("", book.getDescription());
    }

    @Test
    public void testToString() {
        System.out.println("\nStarting ToString");

        Book book = new Book("Dune", "Frank Herbert", 0, Genre.SciFi, "9780399128967");

        String expectedString = "Book name:'Dune', author:'Frank Herbert', genre:'Sci-Fi', ISBN:'9780399128967'";
        assertEquals(expectedString, book.toString());

        System.out.println("\nFinished ToString");
    }
}