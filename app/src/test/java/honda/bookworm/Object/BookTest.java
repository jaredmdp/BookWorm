package honda.bookworm.Object;

import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void testBook() {

        Book book;

        System.out.println("\nStarting BookTest");

        //Test Get;
        book = new Book("The Way of Kings", "Brandon Sanderson", "Fantasy Fiction", "9780765326355");
        assertNotNull(book);
        assertEquals("The Way of Kings", book.getName());
        assertEquals("Brandon Sanderson", book.getAuthor());
        assertEquals("Fantasy Fiction", book.getGenre());
        assertEquals("9780765326355", book.getISBN());

        //Test Set
        book.setName("Harry Potter and the Philosopher's Stone");
        book.setAuthor("JK Rowling");
        book.setISBN("9780747532743");
        book.setGenre("Fantasy");
        assertEquals("Harry Potter and the Philosopher's Stone", book.getName());
        assertEquals("JK Rowling", book.getAuthor());
        assertEquals("Fantasy", book.getGenre());
        assertEquals("9780747532743", book.getISBN());


        //Test toString()
        String expectedString = "Book name:'Harry Potter and the Philosopher's Stone', author:'JK Rowling', genre:'Fantasy', ISBN:'9780747532743'";
        assertEquals(expectedString, book.toString());

        //Test equals()
        Book identicalBook = new Book("Harry Potter and the Philosopher's Stone", "JK Rowling", "Fantasy", "9780747532743");
        assertEquals(identicalBook, book);

        Book differentBook = new Book("The Way of Kings", "Brandon Sanderson", "Fantasy Fiction", "9780765326355");
        assertNotEquals(differentBook, book);

        System.out.println("\nFinished BookTest");

    }
}