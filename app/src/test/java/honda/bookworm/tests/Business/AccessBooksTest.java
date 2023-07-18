package honda.bookworm.tests.Business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.Books.InvalidBookException;
import honda.bookworm.Business.Exceptions.InvalidGenreException;
import honda.bookworm.Business.Exceptions.Users.AuthorNotFoundException;
import honda.bookworm.Business.ISearchManager;
import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Business.Managers.SearchManager;
import honda.bookworm.Data.Stubs.BookPersistenceStub;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

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
    public void testAddBookSuccess() {
        System.out.println("\nStarting testAddBookSuccess");

        Book result = null;
        try {
            result = accessBooks.addBook("Awesome Book", Genre.SciFi, "1111111111111", "asd", "", true);
        } catch (Exception e) {
            fail();
        }

        List<Book> bookList = accessBooks.getAuthorIDBookList(0);
        boolean isBookAdded = bookList.contains(result);

        assertTrue(isBookAdded);

        System.out.println("\nFinished testAddBookSuccess");
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
    public void testAddBookISBN_FailLength(){
        System.out.println("\nStarting testAddBookISBNFail");

        String wrongISBNLength = "123456";

        try{
            accessBooks.addBook("asd", Genre.SciFi, wrongISBNLength, "asd", "", true);
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

    @Test
    public void testAddBookGenreFail(){
        System.out.println("\nStarting testAddBookGenreFail");

        try{
            accessBooks.addBook("asd", null, "1111111111111", "asd", "", true);
            assert(false);
        }
        catch(Exception e){
            assert(e instanceof InvalidGenreException);
        }

        System.out.println("\nFinished testAddBookGenreFail");
    }

    @Test
    public void testBookByISBN_Success() {
        System.out.println("\nStarting testBookByISBN");

        Book result = accessBooks.getBookByISBN("5780100220888");
        assertEquals(result.getName(), "Atomic Habits");
        assertEquals(result.getISBN(), "5780100220888");
        assertEquals(result.getAuthor(), "James Clear");
        assertEquals(result.getAuthorID(), 15);

        System.out.println("\nFinished testBookByISBN");
    }

    @Test
    public void testGetBookByAuthorID() {
        System.out.println("\nStarting testGetBookByAuthorID");
        List<Book> newList = new ArrayList<>();
        try {
            newList = accessBooks.getAuthorIDBookList(0);
        } catch (AuthorNotFoundException e) {
            fail(); //should not error
        }

        assertEquals(newList.size(), 5);

        System.out.println("\nFinished testGetBookByAuthorID");
    }

    @Test
    public void testGetBookByAuthorID_Fail() {
        System.out.println("\nStarting testGetBookByAuthorID_Fail");
        try {
            assertThrows(AuthorNotFoundException.class, () -> accessBooks.getAuthorIDBookList(-100));
        } catch (AuthorNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("\nFinished testGetBookByAuthorID_Fail");
    }

    @Test
    public void testAddBookNonAuthor_Fail () {
        System.out.println("\nStarting testAddBookNonAuthor_Fail");

        User newUser = new User("Jared", "Mandap", "mandapj", "pass123");
        Services.setActiveUser(newUser);

        assertThrows(IllegalStateException.class, () -> accessBooks.addBook("asd", Genre.SciFi, "1111111111111", "", "", true));

        System.out.println("\nFinished testAddBookNonAuthor_Fail");
    }

    @Test
    public void testGetAllAvailableGenres(){
        System.out.println("\nStarting testAllAvailableGenres");

        List<Genre> allGenres = accessBooks.getAllAvailableGenres();

        assertTrue(allGenres.size()<= (Genre.values()).length);
        assertFalse(allGenres.size() > (Genre.values()).length);

        System.out.println("\nFinished testAllAvailableGenres");
    }

}
