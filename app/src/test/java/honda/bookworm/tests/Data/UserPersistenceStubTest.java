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
    public void testAddUser(){
        System.out.println("\nStarting AddUser");

        User user = userStub.addUser(new User("Mama", "Mia", "mamamia", "password"));

        assertNotNull(user);

        System.out.println("\nFinished AddUser");
    }

    @Test
    public void testUpdateUser(){
        System.out.println("\nStarting UpdateUser");

        User currentUser = new User("John", "Doe", "johndoe", "password1");
        User updateUser = new User("Johnny", "Depp", "johndoe", "password1");
        User user = userStub.updateUser(currentUser, updateUser);

        assertNotNull(user);

        System.out.println("\nFinished UpdateUser");
    }

    @Test
    public void testRemoveUser(){
        System.out.println("\nStarting RemoveUser");
        User user = userStub.removeUser(new User("Joe", "Mama", "joemama", "password4"));

        assertNotNull(user);

        System.out.println("\nFinished RemoveUser");
    }
}
