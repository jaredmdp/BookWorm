package honda.bookworm.tests.Business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Data.hsqldb.BookPersistenceHSQLDB;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.tests.utils.TestUtils;

public class AccessBooksIT {
    private AccessBooks accessBooks;
    private File tempDB;

    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final IBookPersistence persistence = new BookPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.accessBooks = new AccessBooks(persistence);
    }

    @Test
    public void testGetBooksGenreFound(){
        System.out.println("\nStarting testGetBooksGenreFound");

        List<Book> books = accessBooks.getBooksGenre(Genre.Fantasy);
        assertEquals(5, books.size());

        System.out.println("\nFinished testGetBooksGenreFound");
    }

    @Test
    public void testAddBook(){
        System.out.println("\nStarting testAddBook");

        Book book = new Book("Sword of Destiny", "Andrzej Sapkowski", 0, Genre.Adult, "970575077832",
                "Geralt is a witcher, a man whose magic powers, enhanced by long training and a mysterious elixir, have made him a brilliant fighter and a merciless assassin. Yet he is no ordinary murderer: his targets are the multifarious monsters and vile fiends that ravage the land and attack the innocent.\n" +
                        "\n" +
                        "This is a collection of short stories, following the adventures of the hit collection THE LAST WISH. Join Geralt as he battles monsters, demons and prejudices alike..."
        );
        assertNotNull(book);
        Book addedBook = accessBooks.addBook(book);
        assertEquals(book, addedBook);

        System.out.println("\nFinished testAddBook");
    }

    @After
    public void tearDown(){
        this.tempDB.delete();
    }

}
