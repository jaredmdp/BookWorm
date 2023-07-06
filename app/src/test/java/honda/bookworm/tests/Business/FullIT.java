package honda.bookworm.tests.Business;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import honda.bookworm.Business.Exceptions.Books.InvalidISBNException;
import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
import honda.bookworm.Business.IAccessBooks;
import honda.bookworm.Business.IAccessUsers;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.Managers.AccessBooks;
import honda.bookworm.Business.Managers.AccessUsers;
import honda.bookworm.Business.Managers.UserManager;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Data.hsqldb.BookPersistenceHSQLDB;
import honda.bookworm.Data.hsqldb.UserPersistenceHSQLDB;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;
import honda.bookworm.tests.utils.TestUtils;

public class FullIT {
    private IAccessUsers accessUsers;
    private IUserManager manager;
    private IAccessBooks accessBooks;
    private File tempDB;

    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final IUserPersistence persistence = new UserPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IBookPersistence bPersistence = new BookPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.accessUsers = new AccessUsers(persistence);
        this.manager = new UserManager(persistence);
        this.accessBooks = new AccessBooks(bPersistence);
    }

    @Test
    public void testFullNewAuthorAddsBook(){
        System.out.println("\nStarting testFullNewAuthorAddsBook");

        accessUsers.addNewUser("John", "Doe", "johndoe", "password1", false);
        accessUsers.addNewUser("John", "Depp", "jondepp", "password2", false);

        String firstName = "Roboute";
        String lastName = "Guilliman";
        String username = "XenosHater";
        String password = "Password";

        List<User> allUsersOld = manager.getAllUsers();
        Author author = (Author)(accessUsers.addNewUser(firstName, lastName, username, password, true));
        List<User> allUsersNew = manager.getAllUsers();

        assert(manager.getActiveUser().equals(author));
        assert(author.getFirstName().equals(firstName));
        assert(author.getLastName().equals(lastName));
        assert(author.getUsername().equals(username));
        assert(author.getPassword().equals(password));
        assert(author.getAuthorID() < allUsersNew.size() && author.getAuthorID() >= 0);
        assert(!(allUsersOld.contains(author)));
        assert(allUsersNew.contains(author));
        assert(allUsersNew.size() == allUsersOld.size()+1);

        String bookName = "Codex Astartes";
        Genre genre = Genre.NonFiction;
        String ISBN = "40000";
        String description = "Guidelines for the Astartes legions";

        Book book = new Book(bookName, author.getFirstName()+ " " + author.getLastName()
            , author.getAuthorID(), genre, ISBN, description);

        List<Book> getBooksOld = accessBooks.getBooksGenre(genre);
        Book inserted = accessBooks.addBook(book);
        List<Book> getBooksNew = accessBooks.getBooksGenre(genre);

        assert(inserted.getName().equals(bookName));
        assert(inserted.getAuthor().equals(author.getFirstName()+ " " + author.getLastName()));
        assert(inserted.getAuthorID() == author.getAuthorID());
        assert(inserted.getGenre().equals(genre));
        assert(inserted.getISBN().equals(ISBN));
        assert(inserted.getDescription().equals(description));
        assert(!(getBooksOld).contains(inserted));
        assert(getBooksNew.contains(inserted));
        assert(getBooksNew.size() == getBooksOld.size()+1);

        System.out.println("\nFinished testFullNewAuthorAddsBook");

    }

    @Test
    public void testBookFavoriteToggle(){
        System.out.println("\nStarting testBookFavoriteToggle");
        boolean result;

        //test with a no active user. invalid user cant access it
        try{
            result = accessBooks.bookFavouriteToggle("9780199536269");
            assertFalse("this should not be called",result);
        }catch (Exception e){
            assert(e instanceof GeneralPersistenceException);
        }

        //log in to user
        accessUsers.verifyUser("rowling","harrypotter");
        try{
            result = accessBooks.bookFavouriteToggle("9780199536269");
            assertTrue(result);

            result = accessBooks.bookFavouriteToggle("9780199536269");
            assertFalse(result);
        }catch (Exception e){
            assertFalse("This should not be called", true);
        }


        try{
            result = accessBooks.bookFavouriteToggle("178019953629");
            assertFalse("this should not be called",result);
        }catch (Exception e){
            assert(e instanceof InvalidISBNException);
        }

        System.out.println("\nFinished testBookFavoriteToggle");

    }

    @Test
    public void testIsBookFavorite(){
        System.out.println("\nStarting testIsBookFavorite");
        boolean result;
        User u = null;

        try {
            result = accessBooks.isBookFavourite(u, "97199536269"); //null user and fake isbn
            assert(!result);

            result = accessBooks.isBookFavourite(u, "9780199536269"); // null user, valid isbn
            assert(!result);

            u = accessUsers.addNewUser("Test","User", "testUser","pass123",false);
            result = accessBooks.isBookFavourite(u, "9780199536269"); // valid user and isbn but not favorite
            assert(!result);

            accessBooks.bookFavouriteToggle("9780199536269");

            result = accessBooks.isBookFavourite(u, "9780199536269"); // valid user, isbn and book favorite
            assert(result);

            result = accessBooks.isBookFavourite(u, "97199536269"); // valid user and invalid isbn
            assert(!result);
        }catch(Exception e){
            assert (false); //exception should not be thrown
        }

        System.out.println("\nFinished testIsBookFavorite");
    }

    @Test
    public void testGetFavouriteBookList(){
        System.out.println("\nStarting testGetBookFavoriteBookList");
        List<Book> bookList = null;
        User testUser = null;

        try{
            bookList = accessBooks.getFavoriteBookList(testUser);
            assert(bookList.isEmpty());

        }catch (Exception e) {
            assert(!bookList.isEmpty());
            assert(false);
        }

        testUser = new User("hello","world","testUser", "passwd"); //fake user

        try{
            bookList = accessBooks.getFavoriteBookList(testUser);
            assert(bookList.isEmpty());

        }catch (Exception e) {
            assert(!bookList.isEmpty());
            assert(false);
        }

        accessUsers.verifyUser("rowling","harrypotter");
        testUser = manager.getActiveUser();



        try{
            bookList = accessBooks.getFavoriteBookList(testUser);
            assert(bookList.isEmpty());

            accessBooks.bookFavouriteToggle("9780007123803");
            accessBooks.bookFavouriteToggle("9780141198888");

            bookList = accessBooks.getFavoriteBookList(testUser);
            assert(!bookList.isEmpty());
            assert(bookList.size() == 2);

            accessBooks.bookFavouriteToggle("9780007123803");

            bookList = accessBooks.getFavoriteBookList(testUser);
            assert(!bookList.isEmpty());
            assert(bookList.size() == 1);

            accessBooks.bookFavouriteToggle("9780141198888");

            bookList = accessBooks.getFavoriteBookList(testUser);
            assert(bookList.isEmpty());

        }catch (Exception e) {
            assert(!bookList.isEmpty());
        }

        System.out.println("Finished testGetBookFavoriteBookList.\n");
    }

    @After
    public void tearDown(){
        this.tempDB.delete();
    }
}
