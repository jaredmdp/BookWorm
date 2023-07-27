package honda.bookworm.tests.Business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.Books.DuplicateISBNException;
import honda.bookworm.Business.Exceptions.Books.InvalidISBNException;
import honda.bookworm.Business.Exceptions.Users.AuthorNotFoundException;
import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Data.hsqldb.BookPersistenceHSQLDB;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.tests.utils.TestUtils;

public class AccessBooksIT {
    private AccessBooks accessBooks;
    private File tempDB;

    private Author testAuthor;

    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final IBookPersistence persistence = new BookPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.accessBooks = new AccessBooks(persistence);

        testAuthor = new Author("first", "last", "test", "pass", 0);

        Services.setActiveUser(testAuthor);
    }

    @Test
    public void testAddBook(){
        System.out.println("\nStarting testAddBook");

        Book book = new Book("Sword of Destiny", testAuthor.getFirstName()+" "+testAuthor.getLastName(), testAuthor.getAuthorID(),Genre.Adult, "9705750778328",
                "Geralt is a witcher, a man whose magic powers, enhanced by long training and a mysterious elixir, have made him a brilliant fighter and a merciless assassin. Yet he is no ordinary murderer: his targets are the multifarious monsters and vile fiends that ravage the land and attack the innocent.\n" +
                        "\n" +
                        "This is a collection of short stories, following the adventures of the hit collection THE LAST WISH. Join Geralt as he battles monsters, demons and prejudices alike...",
                "Test Cover", true);
        assertNotNull(book);
        Book addedBook = accessBooks.addBook(book.getName(), book.getGenre(), book.getISBN(), book.getDescription(), book.getCover(), book.getPurchaseable());
        assertEquals(book, addedBook);

        System.out.println("\nFinished testAddBook");
    }

    @Test
    public void testAddDuplicateISBNBook(){
        System.out.println("\nStarting testAddDuplicateISBNBook");

        Book book = new Book("To Kill a Mockingbird", testAuthor.getFirstName()+" "+testAuthor.getLastName(), testAuthor.getAuthorID(), Genre.Fiction, "9780061120084",
                "To Kill a Mockingbird is a novel by Harper Lee published in 1960. It is set in the fictional town of Maycomb, Alabama, during the Great Depression, and follows the story of Scout Finch as she grows up and learns about racial injustice in her community. The book explores themes of morality, compassion, and the loss of innocence. It is widely regarded as a classic of American literature.",
                "Test Cover", true);

        assertNotNull(book);
        Book addedBook = accessBooks.addBook(book.getName(), book.getGenre(), book.getISBN(), book.getDescription(), book.getCover(), book.getPurchaseable());
        assertEquals(book, addedBook);

        try {
            addedBook = accessBooks.addBook(book.getName(), book.getGenre(), book.getISBN(), book.getDescription(), book.getCover(), book.getPurchaseable());  //add same book
            fail();
        } catch (DuplicateISBNException e) {
            System.out.println("Success: Duplicate ISBN found: " + e.getMessage());
        }

        System.out.println("\nFinished testAddDuplicateISBNBook");
    }

    @Test
    public void testGetBookByISBN() {
        String validISBN = "9780199536269";
        String invalidISBN = "007";
        System.out.println("\n Testing testGetBookByISBN");
        Book book = null;

        try{
            book = accessBooks.getBookByISBN(validISBN);
            assertEquals(book.getISBN(), validISBN);
        }catch (Exception e){
            assertNotNull("This should not be null",book);
            fail("This should not be called");
            System.out.println(e.getMessage());
        }

        book = null;

        try{
            book = accessBooks.getBookByISBN(invalidISBN);
            assertNotNull("The book should be null and should not call this assert", book);
        }catch (InvalidISBNException e){
            assertNull("Book should be null: ",book);
            System.out.println(e.getMessage());
        }


        System.out.println("\n Finished testGetBookByISBN");
    }

    @Test
    public void testGetBookByAuthorID() {
        System.out.println("\nStarting testGetBookByAuthorID");
        List<Book> books = accessBooks.getAuthorIDBookList(0); //Author: 0 = Jane Austen in script
        assertEquals(5, books.size());
        System.out.println("\nFinished testGetBookByAuthorID");
    }

    @Test
    public void testGetBookByAuthorID_Fail() {
        System.out.println("\nStarting testGetBookByAuthorID_Fail");
        assertThrows(AuthorNotFoundException.class, () -> accessBooks.getAuthorIDBookList(-100));
        System.out.println("\nFinished testGetBookByAuthorID_Fail");
    }

    @Test
    public void testGetAllAvailableGenres(){
        System.out.println("\nStarting testAllAvailableGenres");

        List<Genre> allGenres = accessBooks.getAllAvailableGenres();

        assertTrue(allGenres.size()<= (Genre.values()).length);
        assertFalse(allGenres.size() > (Genre.values()).length);

        System.out.println("\nFinished testAllAvailableGenres");
    }

    @After
    public void tearDown() {
        this.tempDB.delete();
    }

}
