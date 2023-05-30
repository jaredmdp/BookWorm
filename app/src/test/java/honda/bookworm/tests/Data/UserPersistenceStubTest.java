package honda.bookworm.tests.Data;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Data.Stubs.UserPersistenceStub;
import honda.bookworm.Object.User;

public class UserPersistenceStubTest {
    private UserPersistenceStub userStub;
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
        usersTest.add(new User("Joe", "Mama", "joemama", "password4"));

        assertNotNull(users);
        assertNotNull(usersTest);
        assertTrue(users.equals(usersTest));

        System.out.println("\nFinished GetAllUsers");
    }

    @Test
    public void testGetUserByUsernameValid(){
        System.out.println("\nStarting GetUserByUsernameValid");

        User user = userStub.getUserByUsername("johndoe");
        User userTest = new User("John", "Doe", "johndoe", "password1");

        assertNotNull(user);
        assertNotNull(userTest);
        assertTrue(user.equals(userTest));

        System.out.println("\nFinished GetUserByUsernameValid");
    }

    @Test
    public void testGetUserByUsernameInvalid(){
        System.out.println("\nStarting GetUserByUsernameInvalid");

        User user = userStub.getUserByUsername("hello");

        assertNull(user);

        System.out.println("\nFinished GetUserByUsernameInvalid");
    }

    @Test
    public void testAddUserValid(){
        System.out.println("\nStarting AddUserValid");

        User user = userStub.addUser(new User("Mama", "Mia", "mamamia", "password"));

        assertNotNull(user);

        System.out.println("\nFinished AddUserValid");
    }

    @Test
    public void testAddUserInvalid(){
        System.out.println("\nStarting AddUserInvalid");

        User user = userStub.addUser(new User("John", "Doe", "johndoe", "password1"));

        assertNull(user);

        System.out.println("\nFinished AddUserInvalid");
    }

    @Test
    public void testUpdateUserValid(){
        System.out.println("\nStarting UpdateUserValid");

        User currentUser = new User("John", "Doe", "johndoe", "password1");
        User updateUser = new User("Johnny", "Depp", "johndoe", "password1");
        User user = userStub.updateUser(currentUser, updateUser);

        assertNotNull(user);

        System.out.println("\nFinished UpdateUserValid");
    }

    @Test
    public void testUpdateUserInvalid(){
        System.out.println("\nStarting UpdateUserInvalid");

        User currentUser = new User("unknown", "test", "nope", "password");
        User updateUser = new User("Johnny", "Depp", "johndoe", "password1");
        User user = userStub.updateUser(currentUser, updateUser);

        assertNull(user);

        System.out.println("\nFinished UpdateUserInvalid");
    }

    @Test
    public void testRemoveUserValid(){
        System.out.println("\nStarting RemoveUserValid");
        User user = userStub.removeUser(new User("Joe", "Mama", "joemama", "password4"));

        assertNotNull(user);

        System.out.println("\nFinished RemoveUserValid");
    }

    @Test
    public void testRemoveUserInvalid(){
        System.out.println("\nStarting RemoveUserInvalid");
        User user = userStub.removeUser(new User("unknown", "test", "nope", "password"));

        assertNull(user);

        System.out.println("\nFinished RemoveUserInvalid");
    }
}
