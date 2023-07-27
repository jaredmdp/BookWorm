package honda.bookworm.tests.Business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.Books.InvalidBookException;
import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
import honda.bookworm.Business.Exceptions.InvalidGenreException;
import honda.bookworm.Business.Managers.UserPreference;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

public class UserPreferenceTest {
    private UserPreference userPreference;
    private IBookPersistence bookPersistence;
    private IUserPersistence userPersistence;

    @Before
    public void setup() {
        System.out.println("\nStarting test for UserPreference");
        bookPersistence = mock(IBookPersistence.class);
        userPersistence = mock(IUserPersistence.class);
        userPreference = new UserPreference(userPersistence, bookPersistence);
    }

    @Test
    public void testGetBookRecommendationsWithNoActiveUser() throws GeneralPersistenceException {
        System.out.println("Starting testGetBookRecommendationsWithNoActiveUser");
        List<Book> mostFavoriteBooks = new ArrayList<>();
        Services.setActiveUser(null);
        mostFavoriteBooks.add(new Book("Book1", "Author1", 1, Genre.Fiction, "1234567890", "", "", true));
        mostFavoriteBooks.add(new Book("Book2", "Author2", 1, Genre.Romance, "2345678901", "", "", true));
        mostFavoriteBooks.add(new Book("Book3", "Author3", 1, Genre.Mystery, "3456789012", "", "", true));

        when(bookPersistence.getMostFavoriteBooks()).thenReturn(mostFavoriteBooks);

        List<Book> result = userPreference.getBookRecommendations();

        assertEquals(3, result.size()); // Expecting all 3 most favorite books to be recommended
        System.out.println("Finished testGetBookRecommendationsWithNoActiveUser");
    }

    @Test
    public void testGetBookRecommendationsWithActiveUser() throws GeneralPersistenceException {
        System.out.println("Starting testGetBookRecommendationsWithActiveUser");

        // Create an active user and set it in the Services
        User activeUser = new User("john_doe", "John", "Doe", "password");
        Services.setActiveUser(activeUser);

        List<Book> userFavoriteFantasy = new ArrayList<>();
        userFavoriteFantasy.add(new Book("Book4", "Author4", 1, Genre.Fantasy, "4567890123", "", "", true));
        userFavoriteFantasy.add(new Book("Book5", "Author5", 1, Genre.Fantasy, "5678901234", "", "", true));

        List<Book> userFavoriteMystery = new ArrayList<>();
        userFavoriteMystery.add(new Book("Book1", "Author1", 1, Genre.Mystery, "1234567890", "", "", true));
        userFavoriteMystery.add(new Book("Book2", "Author2", 1, Genre.Mystery, "2345678901", "", "", true));
        userFavoriteMystery.add(new Book("Book3", "Author3", 1, Genre.Mystery, "3456789012", "", "", true));

        List<Genre> favoriteGenreList = new ArrayList<>();
        favoriteGenreList.add(Genre.Mystery);
        favoriteGenreList.add(Genre.Fantasy);

        when(userPersistence.getFavoriteGenreList(activeUser)).thenReturn(favoriteGenreList);
        when(bookPersistence.getBooksByGenre(Genre.Fantasy)).thenReturn(userFavoriteFantasy);
        when(bookPersistence.getBooksByGenre(Genre.Mystery)).thenReturn(userFavoriteMystery);

        List<Book> result = userPreference.getBookRecommendations();

        // Expecting all 5 books
        assertEquals(5, result.size());
        System.out.println("Finished testGetBookRecommendationsWithActiveUser");
    }

    @Test
    public void testIsGenreFavouriteWithNullUser(){
        System.out.println("Starting testIsGenreFavouriteWithNullUser");
        assertFalse(userPreference.isGenreFavourite(null, Genre.Adult));
        System.out.println("Finished testAddUserFailEmptyStrings");
    }

    @Test
    public void testIsGenreFavouriteWithNullGenre(){
        System.out.println("Starting testIsGenreFavouriteWithNullGenre");
        User user = new User("joe", "mamma", "jm","pass123");
        assertFalse(userPreference.isGenreFavourite(user, null));
        System.out.println("Finished testIsGenreFavouriteWithNullGenre");
    }

    @Test
    public void testIsGenreFavouriteWithValidUserAndGenreNoFavorites(){
        System.out.println("Starting testIsGenreFavouriteWithValidUserAndGenreNoFavorites");
        User user = new User("joe", "mamma", "jm","pass123");
        assertFalse(userPreference.isGenreFavourite(user, Genre.Adult));
        System.out.println("Finished testIsGenreFavouriteWithValidUserAndGenreNoFavorites");
    }

    @Test
    public void testGenreFavouriteToggleWithNoActiveUser(){
        System.out.println("Starting testGenreFavouriteToggleWithNoActiveUser");
        assertFalse(userPreference.genreFavouriteToggle(Genre.Action));
        System.out.println("Finished testGenreFavouriteToggleWithNoActiveUser");
    }

    @Test
    public void testGetFavoriteGenreListWithNullUser(){
        System.out.println("Starting testGetFavoriteGenreListWithNullUser");
        assertTrue(userPreference.getFavoriteGenreList(null).isEmpty());
        System.out.println("Finished testGetFavoriteGenreListWithNullUser");
    }

    @Test
    public void testGetFavoriteGenreListWithValidUserNoFavorites(){
        User user = new User("yahoo","mail","hacked","three3");
        System.out.println("Starting testGetFavoriteGenreListWithValidUserNoFavorites");
        assertTrue(userPreference.getFavoriteGenreList(user).isEmpty());
        System.out.println("Finished testGetFavoriteGenreListWithValidUserNoFavorites");
    }

    @Test
    public void testIsBookFavouriteWithUserNull(){
        System.out.println("Starting testIsBookFavouriteWithUserNull");
        assertFalse(userPreference.isBookFavourite(null,"12345678910"));
        System.out.println("Finished testIsBookFavouriteWithUserNull");
    }

    @Test
    public void testIsBookFavouriteWithNullISBN(){
        System.out.println("Starting testIsBookFavouriteWithValidUserNoFavouriteBook");
        User user = new User("joe", "mamma", "jm","pass123");
        assertFalse(userPreference.isBookFavourite(user,null));
        System.out.println("Finished testIsBookFavouriteWithValidUserNoFavouriteBook");
    }

    @Test
    public void testIsBookFavouriteWithValidUserNoFavouriteBook(){
        System.out.println("Starting testIsBookFavouriteWithValidUserNoFavouriteBook");
        User user = new User("joe", "mamma", "jm","pass123");
        assertFalse(userPreference.isBookFavourite(user,"12345678910"));
        System.out.println("Finished testIsBookFavouriteWithValidUserNoFavouriteBook");
    }

    @Test
    public void testBookFavouriteToggleNoActiveUser(){
        System.out.println("Starting testBookFavouriteToggleNoActiveUser");
        assertFalse(userPreference.bookFavouriteToggle("12345678910"));
        System.out.println("Finished testBookFavouriteToggleNoActiveUser");
    }

    @Test
    public void testGetFavoriteBookListWithNullUser(){
        System.out.println("Starting testGetFavoriteBookListWithNullUser");
        assertTrue(userPreference.getFavoriteBookList(null).isEmpty());
        System.out.println("Finished testGetFavoriteBookListWithNullUser");
    }

    @Test
    public void testGetFavoriteBookListWithValidUserNoFavorites(){
        User user = new User("yahoo","mail","hacked","three3");
        System.out.println("Starting testGetFavoriteBookListWithValidUserNoFavorites");
        assertTrue(userPreference.getFavoriteBookList(user).isEmpty());
        System.out.println("Finished testGetFavoriteBookListWithValidUserNoFavorites");
    }

    @Test
    public void testMockGenrealPersistenceExceptions(){
        System.out.println("Starting testMockGenrealPersistenceExceptions");
        User fakeUser = new User("","","","");
        User fakeUser2 = new User("123","456","asd","");
        List<Genre> genreList = new ArrayList<>();
        genreList.add(null);

        when(bookPersistence.getMostFavoriteBooks()).thenThrow(GeneralPersistenceException.class);
        assertThrows(InvalidBookException.class,()->userPreference.getBookRecommendations());

        Services.setActiveUser(fakeUser);
        when(userPersistence.getFavoriteGenreList(fakeUser)).thenThrow(GeneralPersistenceException.class);
        assertThrows(InvalidGenreException.class,()->userPreference.getBookRecommendations());

        Services.setActiveUser(fakeUser2);
        when(userPersistence.getFavoriteGenreList(fakeUser2)).thenReturn(genreList);
        when(bookPersistence.getBooksByGenre(null)).thenThrow(GeneralPersistenceException.class);
        assertThrows(InvalidGenreException.class,()->userPreference.getBookRecommendations());

        System.out.println("Finished testMockGenrealPersistenceExceptions");
    }

}