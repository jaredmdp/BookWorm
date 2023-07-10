package honda.bookworm.tests.Business;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.Books.InvalidBookException;
import honda.bookworm.Business.ISearchManager;
import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Business.Managers.SearchManager;
import honda.bookworm.Data.Stubs.BookPersistenceStub;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

public class AccessBooksTest {
    private AccessBooks accessBooks;
    private ISearchManager accessSearch;

    @Before
    public void setup() {
        System.out.println("\nStarting test for AccessBooks");
        accessBooks = new AccessBooks(new BookPersistenceStub());
        accessSearch = new SearchManager(new BookPersistenceStub());

        Author testAuthor = new Author("First", "Last", "User", "Pass", 0);
        Services.setActiveUser(testAuthor);
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
        assertEquals("Trimmed title must be equal to actual book title", testBook.getName().length(), trimmed.length());

        System.out.println("\nFinished getTrimmedBookTitles Test");
    }

    @Test
    public void testAddBookTitleFail(){
        System.out.println("\nStarting testAddBookTitleFail");

        String wrongTitleShort = "";
        String wrongTitleLong = "11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";

        try{
            accessBooks.addBook(wrongTitleShort, Genre.SciFi, "1111111111111", "asd", "", true);
            assert(false);
        }
        catch(Exception e){
            assert(e instanceof InvalidBookException);
        }

        try{
            accessBooks.addBook(wrongTitleLong, Genre.SciFi, "1111111111111", "asd", "", true);
            assert(false);
        }
        catch(Exception e){
            assert(e instanceof InvalidBookException);
        }

        System.out.println("\nFinished testAddBookTitleFail");
    }

    @Test
    public void testAddBookISBNFail(){
        System.out.println("\nStarting testAddBookISBNFail");

        String wrongISBNLength = "";
        String wrongISBNLetters = "111111111111a";

        try{
            accessBooks.addBook("asd", Genre.SciFi, wrongISBNLength, "asd", "", true);
            assert(false);
        }
        catch(Exception e){
            assert(e instanceof InvalidBookException);
        }

        try{
            accessBooks.addBook("asd", Genre.SciFi, wrongISBNLetters, "asd", "", true);
            assert(false);
        }
        catch(Exception e){
            assert(e instanceof InvalidBookException);
        }

        System.out.println("\nFinished testAddBookISBNFail");
    }

    @Test
    public void testAddBookDescriptionFail(){
        System.out.println("\nStarting testAddBookDescriptionFail");

        String wrongDescriptionShort = "";
        String wrongDescriptionLong = "11111111111111111111111111111111111111111111111111";

        for(int i=0; i<20; i++)
        {
            wrongDescriptionLong = wrongDescriptionLong + wrongDescriptionLong;
        }

        try{
            accessBooks.addBook("asd", Genre.SciFi, "1111111111111", wrongDescriptionLong, "", true);
            assert(false);
        }
        catch(Exception e){
            assert(e instanceof InvalidBookException);
        }

        try{
            accessBooks.addBook("asd", Genre.SciFi, "1111111111111", wrongDescriptionShort, "", true);
            assert(false);
        }
        catch(Exception e){
            assert(e instanceof InvalidBookException);
        }

        System.out.println("\nFinished testAddBookDescriptionFail");
    }

    @Test
    public void testAddBookCoverFail(){
        System.out.println("\nStarting testAddBookCoverFail");

        try{
            accessBooks.addBook("asd", Genre.SciFi, "1111111111111", "asd", null, true);
            assert(false);
        }
        catch(Exception e){
            assert(e instanceof InvalidBookException);
        }

        System.out.println("\nFinished testAddBookCoverFail");
    }
}
