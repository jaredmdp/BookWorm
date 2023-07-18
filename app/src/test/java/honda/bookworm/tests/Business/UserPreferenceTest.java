package honda.bookworm.tests.Business;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import honda.bookworm.Business.Managers.UserPreference;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Data.IUserPersistence;
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
}
