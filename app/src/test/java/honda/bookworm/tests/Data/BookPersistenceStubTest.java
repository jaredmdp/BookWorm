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

        dummyBooks.add(new Book ("The Last Wish","Andrzej Sapkowski", Genre.Adult,"0575077832") );
        dummyBooks.add(new Book ("Blood of Elves","Andrzej Sapkowski", Genre.Adult,"9780316029193") );
        dummyBooks.add(new Book ("Sword of Destiny","Andrzej Sapkowski", Genre.Adult,"970575077832") );
        dummyBooks.add(new Book ("The Time of Contempt","Andrzej Sapkowski", Genre.Adult,"0316219134") );
        dummyBooks.add(new Book ("The Fake Book","Jouttun Tall", Genre.Fiction,"12345678909") );
        dummyBooks.add(new Book ("Totally Fake Book","Vanier Myth", Genre.Fiction,"92345678909") );
        dummyBooks.add(new Book ("Not a Fake Book","Vanier Myth", Genre.Fiction,"192345668809") );
        dummyBooks.add(new Book ("Again to the Past","Nurse Brown", Genre.Fiction,"192345768809") );
        dummyBooks.add(new Book ("Again to the Past Once More","Nurse Brown", Genre.Fiction,"192342768809") );
        dummyBooks.add(new Book ("It Depends!","Who Noels", Genre.Fiction,"992342968809") );
        dummyBooks.add(new Book ("The Wager","David Grann",Genre.Survival ,"292345768801") );
        dummyBooks.add(new Book ("Life of Pi","Yann Martel", Genre.Survival,"292344468801") );
        dummyBooks.add(new Book ("The Martian","Andy Weir", Genre.Survival,"292344464801") );
        dummyBooks.add(new Book ("Lord of the Flies","William Golding", Genre.Survival,"292346664801") );
        dummyBooks.add(new Book ("The Maze Runner","James Dasher", Genre.Survival,"292346964801") );
        dummyBooks.add(new Book ("On the Island","Tracy Gravis Graves", Genre.Survival,"29234555801") );
        dummyBooks.add(new Book ("Clean Code","Robert C. Martin", Genre.NonFiction,"9780132350884") );
        dummyBooks.add(new Book ("Atomic Habits","James Clear", Genre.NonFiction,"5780100220888") );
        dummyBooks.add(new Book ("The Art of War","Sun Tzu", Genre.NonFiction,"578099920888") );
        dummyBooks.add(new Book ("Introduction to Algorithms","Thomas H. Cormen", Genre.NonFiction,"9780132350884") );
        dummyBooks.add(new Book ("The C Programming Language","Brian W. Kernighan", Genre.NonFiction,"9780132650884") );
        dummyBooks.add(new Book ("Operating Systems: Three Easy Pieces","Remzi H. Arpaci-Dusseau", Genre.NonFiction,"9780100650884") );

        assertNotNull(allBooks);
        assertEquals(27, allBooks.size());
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
    public void testAddDuplicateBook() {
        System.out.println("\nStarting testAddBook");

        //check for duplicate book that is already in the system
        Book dupeBook = new Book("The Way of Kings", "Brandon Sanderson", Genre.Fantasy, "9780765326355");
        Book result = bookStub.addBook(dupeBook);
        assertNotEquals(result, dupeBook);
        assertNull(result);

        //Double check that the book we added is already in the system
        List<Book> allBooks = bookStub.getAllBooks();
        assertTrue(allBooks.contains(dupeBook));

        System.out.println("\nFinished testAddBook");
    }

    @Test
    public void testRemoveBookByISBN() {
        System.out.println("\nStarting testRemoveBookByISBN");

        String ISBN = "9780765311788"; //Elantris book
        List<Book> allBooks = bookStub.getAllBooks();

        assertEquals(27, allBooks.size());
        bookStub.removeBookByISBN(ISBN);

        //update book count
        allBooks = bookStub.getAllBooks();

        Book result = bookStub.getBookByISBN(ISBN);
        assertNull(result);
        assertEquals(26, allBooks.size());

        System.out.println("\nFinished testRemoveBookByISBN");
    }

    @Test
    public void testRemoveBookByTitle() {
        System.out.println("\nStarting testRemoveBookByTitle");

        String Title = "Mistborn";
        List<Book> allBooks = bookStub.getAllBooks();

        assertEquals(27, allBooks.size());
        bookStub.removeBookByTitle(Title);

        //update book count
        allBooks = bookStub.getAllBooks();

        Book result = bookStub.getBookByTitle(Title);
        assertNull(result);
        assertEquals(26, allBooks.size());

        //test book that is not in system
        Title = "Skyward";
        allBooks = bookStub.getAllBooks();
        bookStub.removeBookByTitle(Title);

        //update book count
        allBooks = bookStub.getAllBooks();
        assertEquals(26, allBooks.size());

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

    @Test
    public void testGetBooksByGenre() {
        //test genre that is not in Stub
        Genre genre = Genre.Manga;
        List<Book> genreBooks = bookStub.getBooksByGenre(genre);
        assertNull(genreBooks);

        //test valid author
        genre = Genre.Adult;
        genreBooks = bookStub.getBooksByGenre(genre);
        assertNotNull(genreBooks);
        assertEquals(4, genreBooks.size());
    }

}
