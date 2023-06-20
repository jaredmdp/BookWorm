package honda.bookworm.tests.Data;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Data.Stubs.UserPersistenceStub;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

public class UserPersistenceStubTest {
    private IUserPersistence userStub;
    @Before
    public void setup(){
        userStub = new UserPersistenceStub();
    }

    @Test
    public void testGetAllUsers(){
        System.out.println("\nStarting GetAllUsers");

        List <User> users = userStub.getAllUsers();
        List <User> usersTest = new ArrayList<>();

        usersTest.add(new User("John", "Doe", "johndoe", "password1"));
        usersTest.add(new User("Jane", "Smith", "janesmith", "password2"));
        usersTest.add(new User("John", "Wick", "johnwick", "password3"));
        usersTest.add(new Author("Joe", "Mama", "joemama", "password4"));
        usersTest.add(new Author("Brandon", "Sanderson", "BrandonSanderson", "password4"));

        assertNotNull(users);
        assertNotNull(usersTest);
        for(int i=0; i<users.size(); i++){
            assertTrue(users.get(i).getUsername().equals(usersTest.get(i).getUsername()));
        }
        System.out.println("\nFinished GetAllUsers");
    }

    @Test
    public void testGetAllAuthors(){
        System.out.println("\nStarting GetAllAuthors");

        List <Author> authors = userStub.getAllAuthors();
        List <Author> authorsTest = new ArrayList<>();

        authorsTest.add(new Author("Joe", "Mama", "joemama", "password4"));
        authorsTest.add(new Author("Brandon", "Sanderson", "BrandonSanderson", "password4"));

        assertNotNull(authors);
        assertNotNull(authorsTest);

        for(int i=0; i<authors.size(); i++){
            assertTrue(authors.get(i).getUsername().equals(authorsTest.get(i).getUsername()));
        }

        System.out.println("\nFinished GetAllAuthors");
    }

    @Test
    public void testGetAllWrittenBooks(){
        System.out.println("\nStarting GetAllWrittenBooks");

        List<Book> books = userStub.getAllWrittenBooks("Brandon Sanderson");
        List<Book> booksTest = new ArrayList<>();

        booksTest.add(new Book("The Way of Kings", "Brandon Sanderson", Genre.Fantasy, "9780765326355"));
        booksTest.add(new Book("Mistborn", "Brandon Sanderson", Genre.Fantasy, "9780765350381"));
        booksTest.add(new Book("Words of Radiance", "Brandon Sanderson", Genre.Fantasy, "9780765326362"));
        booksTest.add(new Book("Elantris", "Brandon Sanderson", Genre.Fantasy, "9780765311788"));
        booksTest.add(new Book("The Alloy of Law", "Brandon Sanderson", Genre.Fantasy, "9780765368546"));

        assertNotNull(books);
        assertNotNull(booksTest);

        assertTrue(books.equals(booksTest));

        System.out.println("\nFinished GetAllWrittenBooks");

    }

    @Test
    public void testGetUserByUsername(){
        System.out.println("\nStarting GetUserByUsername");

        User user = userStub.getUserByUsername("johndoe");
        User userTest = new User("John", "Doe", "johndoe", "password1");

        assertNotNull(user);
        assertNotNull(userTest);
        assertTrue(user.equals(userTest));

        System.out.println("\nFinished GetUserByUsername");
    }

    @Test
    public void testGetUserByUsernameNotFound(){
        System.out.println("\nStarting GetUserByUsernameNotFound");

        User user = userStub.getUserByUsername("FindME");
        assertNull(user);

        System.out.println("\nFinished GetUserByUsernameNotFound");
    }

    @Test
    public void testAddUser(){
        System.out.println("\nStarting AddUser");

        User user = userStub.addUser(new User("Mama", "Mia", "mamamia", "password"));

        assertNotNull(user);
        assertEquals(user.getUsername(), "mamamia");

        System.out.println("\nFinished AddUser");
    }

    @Test
    public void testAddDuplicateUser() {
        System.out.println("\nStarting testAddDuplicateUser");

        try {
            //user is already in the system when initialized
            User user = userStub.addUser(new User("John", "Doe", "johndoe", "password1"));
            assertNull(user);
        } catch (IllegalStateException e) {
            System.out.println("Failed to insert user existing user: " + e.getMessage());
        }

        System.out.println("\nFinished testAddDuplicateUser");
    }

    @Test
    public void testRemoveUser(){
        System.out.println("\nStarting RemoveUser");
        User user = userStub.removeUser(new Author("Joe", "Mama", "joemama", "password4"));

        assertNotNull(user);

        System.out.println("\nFinished RemoveUser");
    }
}
