package honda.bookworm.tests.Business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.Books.InvalidISBNException;
import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
import honda.bookworm.Business.Exceptions.InvalidGenreException;
import honda.bookworm.Business.Exceptions.Users.UserException;
import honda.bookworm.Business.Exceptions.Users.UserNotFoundException;
import honda.bookworm.Business.IAccessUsers;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.Managers.AccessUsers;
import honda.bookworm.Business.Managers.UserManager;
import honda.bookworm.Business.IUserPreference;
import honda.bookworm.Business.Managers.UserPreference;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Data.hsqldb.BookPersistenceHSQLDB;
import honda.bookworm.Data.hsqldb.UserPersistenceHSQLDB;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;
import honda.bookworm.tests.utils.TestUtils;

public class UserPreferenceIT {
    private IAccessUsers accessUsers;
    private IUserManager manager;
    private IUserPreference userPreference;
    private File tempDB;

    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final IUserPersistence persistence = new UserPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        final IBookPersistence bPersistence = new BookPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.accessUsers = new AccessUsers(persistence);
        this.manager = new UserManager(persistence);
        this.userPreference = new UserPreference(persistence,bPersistence);
    }

    @Test
    public void testRecommendedBooksNoActiveUser() {
        System.out.println("\nStarting testRecommendedBooksNoActiveUser");
        List<Book> recommended = new ArrayList<>();

        try {
            recommended = userPreference.getBookRecommendations();
        } catch (Exception e) {
            fail();
        }

        assertEquals(recommended.size(), 6);

        System.out.println("\nFinished testRecommendedBooksNoActiveUser");
    }

    @Test
    public void testRecommendedBooksWithActiveUser() {
        System.out.println("\nStarting testRecommendedBooksNoActiveUser");
        List<Book> recommended = new ArrayList<>();

        accessUsers.verifyUser("martin", "got123");

        try {
            recommended = userPreference.getBookRecommendations();
        } catch (Exception e) {
            fail();
        }

        assertEquals(recommended.size(), 7);

        System.out.println("\nFinished testRecommendedBooksNoActiveUser");
    }

    @Test
    public void testGenreFavoriteToggle(){
        System.out.println("\nStarting testGenreFavoriteToggle");
        boolean result;

        //test with a no active user. invalid user cant access it
        try{
            result = userPreference.genreFavouriteToggle(Genre.Action);
            assertFalse("this should not be called",result);
        }catch (Exception e){
            assert(e instanceof GeneralPersistenceException);
        }

        //log in to user and toggle the same genre
        accessUsers.verifyUser("rowling","harrypotter");
        try{
            result = userPreference.genreFavouriteToggle(Genre.Action);
            assertTrue(result);

            result = userPreference.genreFavouriteToggle(Genre.Action);
            assertFalse(result);
        }catch (Exception e){
            fail("This should not be called");
        }

        try{
            result = userPreference.genreFavouriteToggle(null);
            assertFalse("this should not be called",result);
        }catch (Exception e){
            assert(e instanceof InvalidGenreException);
        }

        System.out.println("\nFinished testBookFavoriteToggle");
    }

    @Test
    public void testIsGenreFavorite(){
        System.out.println("\nStarting testIsGenreFavorite");
        boolean result;
        User u = null;

        try {
            result = userPreference.isGenreFavourite(u, null); //null user and fake genre
            assert(!result);

            result = userPreference.isGenreFavourite(u, Genre.Action); // null user, valid genre
            assert(!result);

            u = accessUsers.addNewUser(new User("Test","User", "testUser","pass123"));
            result = userPreference.isGenreFavourite(u, Genre.Action); // valid user and genre but not favorite
            assert(!result);

            userPreference.genreFavouriteToggle(Genre.Action);

            result = userPreference.isGenreFavourite(u, Genre.Action); // valid user, genre and genre favorite
            assert(result);

            result = userPreference.isGenreFavourite(u, null); // valid user and invalid genre
            assert(!result);
        }catch(Exception e){
            assert (false); //exception should not be thrown
        }

        System.out.println("\nFinished testIsGenreFavorite");
    }


    @Test
    public void testGetFavouriteGenreList(){
        System.out.println("\nStarting testGetFavoriteGenreList");
        List<Genre> genreList = null;
        User testUser = null;

        try{
            genreList = userPreference.getFavoriteGenreList(testUser);
            assert(genreList.isEmpty());

        }catch (Exception e) {
            assert(!Objects.requireNonNull(genreList).isEmpty());
            assert(false);
        }

        testUser = new User("hello","world","testUser", "passwd"); //fake user

        try{
            genreList = userPreference.getFavoriteGenreList(testUser);
            assert(genreList.isEmpty());

        }catch (Exception e) {
            assert(!genreList.isEmpty());
            assert(false);
        }

        accessUsers.verifyUser("rowling","harrypotter");
        testUser = manager.getActiveUser();



        try{
            genreList = userPreference.getFavoriteGenreList(testUser);
            assert(genreList.isEmpty());

            userPreference.genreFavouriteToggle(Genre.Action);
            userPreference.genreFavouriteToggle(Genre.Adult);

            genreList = userPreference.getFavoriteGenreList(testUser);
            assert(!genreList.isEmpty());
            assert(genreList.size() == 2);
            assert(genreList.contains(Genre.Action) && genreList.contains(Genre.Adult));

            userPreference.genreFavouriteToggle(Genre.Adult);

            genreList = userPreference.getFavoriteGenreList(testUser);
            assert(!genreList.isEmpty());
            assert(genreList.size() == 1);
            assert(genreList.contains(Genre.Action) && !genreList.contains(Genre.Adult));


            userPreference.genreFavouriteToggle(Genre.Action);

            genreList = userPreference.getFavoriteGenreList(testUser);
            assert(genreList.isEmpty());

        }catch (Exception e) {
            assert(!genreList.isEmpty());
        }

        System.out.println("Finished testGetFavoriteGenreList.\n");
    }

    @Test
    public void testBookFavoriteToggle(){
        System.out.println("\nStarting testBookFavoriteToggle");
        boolean result;

        //log in to user
        accessUsers.verifyUser("rowling","harrypotter");
        try{
            result = userPreference.bookFavouriteToggle("9780199536269");
            assertTrue(result);

            result = userPreference.bookFavouriteToggle("9780199536269");
            assertFalse(result);
        }catch (Exception e){
            fail("This should not be called");
        }

        try{
            result = userPreference.bookFavouriteToggle("178019953629");
            assertFalse("this should not be called",result);
        }catch (Exception e){
            assert(e instanceof UserException);
        }

        System.out.println("\nFinished testBookFavoriteToggle");

    }

    @Test
    public void testIsBookFavorite(){
        System.out.println("\nStarting testIsBookFavorite");
        boolean result;
        User u = null;

        try {
            result = userPreference.isBookFavourite(u, "97199536269"); //null user and fake isbn
            assert(!result);

            result = userPreference.isBookFavourite(u, "9780199536269"); // null user, valid isbn
            assert(!result);

            u = accessUsers.addNewUser(new User("Test","User", "testUser","pass123"));
            result = userPreference.isBookFavourite(u, "9780199536269"); // valid user and isbn but not favorite
            assert(!result);

            userPreference.bookFavouriteToggle("9780199536269");

            result = userPreference.isBookFavourite(u, "9780199536269"); // valid user, isbn and book favorite
            assert(result);

            result = userPreference.isBookFavourite(u, "97199536269"); // valid user and invalid isbn
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
            bookList = userPreference.getFavoriteBookList(testUser);
            assert(bookList.isEmpty());

        }catch (Exception e) {
            assert(!Objects.requireNonNull(bookList).isEmpty());
            assert(false);
        }

        testUser = new User("hello","world","testUser", "passwd"); //fake user

        try{
            bookList = userPreference.getFavoriteBookList(testUser);
            assert(bookList.isEmpty());

        }catch (Exception e) {
            assert(!bookList.isEmpty());
            assert(false);
        }

        accessUsers.verifyUser("rowling","harrypotter");
        testUser = manager.getActiveUser();



        try{
            bookList = userPreference.getFavoriteBookList(testUser);
            assert(bookList.isEmpty());

            userPreference.bookFavouriteToggle("9780007123803");
            userPreference.bookFavouriteToggle("9780141198888");

            bookList = userPreference.getFavoriteBookList(testUser);
            assert(!bookList.isEmpty());
            assert(bookList.size() == 2);

            userPreference.bookFavouriteToggle("9780007123803");

            bookList = userPreference.getFavoriteBookList(testUser);
            assert(!bookList.isEmpty());
            assert(bookList.size() == 1);

            userPreference.bookFavouriteToggle("9780141198888");

            bookList = userPreference.getFavoriteBookList(testUser);
            assert(bookList.isEmpty());

        }catch (Exception e) {
            assert(!bookList.isEmpty());
        }

        System.out.println("Finished testGetBookFavoriteBookList.\n");
    }

    @After
    public void tearDown(){
        manager.logOutActiveUser();
        this.tempDB.delete();
    }
}
