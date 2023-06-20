package honda.bookworm.tests.Object;

import org.junit.Test;
import static org.junit.Assert.*;

import honda.bookworm.Object.Book;
import honda.bookworm.Object.User;
import honda.bookworm.Object.Genre;

public class UserTest {

    @Test
    public void testNewUser() {
        System.out.println("\nStarting NewUser");

        User user1 = new User("John", "Wick", "Johnwick", "hunter2");
        assertNotNull(user1);

        assertEquals("John", user1.getFirstName());
        assertEquals("Wick", user1.getLastName());
        assertEquals("Johnwick", user1.getUsername());
        assertEquals("hunter2", user1.getPassword());

        assertTrue(user1.getFavoriteGenres().isEmpty());
        assertTrue(user1.getFavoriteBooks().isEmpty());

        System.out.println("\nFinished NewUser");
    }

    @Test
    public void testUserEquals() {
        System.out.println("\nStarting UserEquals");

        User user1 = new User("John", "Wick", "Johnwick", "hunter2");
        User user2 = new User("John", "Wick", "Johnwick", "hunter2");
        assertEquals(user1, user2);
        assertEquals(user1,user1);

        System.out.println("\nFinished UserEquals");
    }

    @Test
    public void testUserNotEquals() {
        System.out.println("\nStarting UserNotEquals");

        User user1 = new User("John", "Doe", "johndoe", "hunter2");
        User user2 = new User("Jane", "Smith", "janesmith", "hunter3");
        assertNotEquals(user1, user2);

        System.out.println("\nFinished UserEquals");
    }

    @Test
    public void testUserToString() {
        System.out.println("\nStarting User toString");

        User user1 = new User("John", "Doe", "johndoe", "hunter3");

        String expectedString = "User: firstName:'John', lastName:'Doe', username:'johndoe', password:'hunter3'";
        assertEquals(expectedString, user1.toString());

        System.out.println("\nFinished User toString");
    }

    //Test Arraylists: Adding and Subtracting books
    @Test
    public void testAddToFavoriteGenres() {
        System.out.println("\nStarting testAddToFavoriteGenres");

        User user = new User("John", "Doe", "johndoe", "hunter2");

        user.addToFavoriteGenres(Genre.Fiction);
        user.addToFavoriteGenres(Genre.Fantasy);

        assertEquals(2, user.getFavoriteGenres().size());
        assertTrue(user.getFavoriteGenres().contains(Genre.Fiction));
        assertTrue(user.getFavoriteGenres().contains(Genre.Fantasy));

        System.out.println("\nFinished testAddToFavoriteGenres");
    }

    @Test
    public void testRemoveFromFavoriteGenres() {
        System.out.println("\nStarting testRemoveFromFavoriteGenres");

        User user = new User("John", "Doe", "johndoe", "hunter2");

        user.addToFavoriteGenres(Genre.Fiction);
        user.addToFavoriteGenres(Genre.Fantasy);

        user.removeFromFavoriteGenres(Genre.Fiction);

        assertEquals(1, user.getFavoriteGenres().size());
        assertFalse(user.getFavoriteGenres().contains(Genre.Fiction));
        assertTrue(user.getFavoriteGenres().contains(Genre.Fantasy));

        System.out.println("\nFinished testRemoveFromFavoriteGenres");
    }

    @Test
    public void testAddToFavoriteBooks() {
        System.out.println("\nStarting AddToFavoriteBooks");

        User user = new User("John", "Doe", "johndoe", "hunter2");

        Book book1 = new Book("Book 1", "Author 1", Genre.Fiction, "1234");
        Book book2 = new Book("Book 2", "Author 2", Genre.Fantasy, "1235");

        user.addToFavoriteBooks(book1);
        user.addToFavoriteBooks(book2);

        assertEquals(2, user.getFavoriteBooks().size());
        assertTrue(user.getFavoriteBooks().contains(book1));
        assertTrue(user.getFavoriteBooks().contains(book2));

        System.out.println("\nFinished AddToFavoriteBooks");
    }

    @Test
    public void testRemoveFromFavoriteBooks() {
        System.out.println("\nStarting RemoveFromFavoriteBooks");

        User user = new User("John", "Doe", "johndoe", "hunter2");

        Book book1 = new Book("Book 1", "Author 1", Genre.Fiction, "1234");
        Book book2 = new Book("Book 2", "Author 2", Genre.Fantasy, "1235");

        user.addToFavoriteBooks(book1);
        user.addToFavoriteBooks(book2);

        user.removeFromFavoriteBooks(book1);

        assertEquals(1, user.getFavoriteBooks().size());
        assertFalse(user.getFavoriteBooks().contains(book1));
        assertTrue(user.getFavoriteBooks().contains(book2));

        System.out.println("\nFinished RemoveFromFavoriteBooks");
    }


    @Test
    public void testIsFavoriteBook() {
        System.out.println("\nStarting isFavoriteBook");

        User user = new User("John", "Doe", "johndoe", "hunter2");

        Book book1 = new Book("Book 1", "Author 1", Genre.Fiction, "1234");
        Book book2 = new Book("Book 2", "Author 2", Genre.Fantasy, "1235");

        user.addToFavoriteBooks(book1);

        assertEquals(1, user.getFavoriteBooks().size());
        assertFalse(user.isFavouriteBook(book2));
        assertTrue(user.isFavouriteBook(book1));

        System.out.println("\nFinished isFavoriteBook");
    }




}
