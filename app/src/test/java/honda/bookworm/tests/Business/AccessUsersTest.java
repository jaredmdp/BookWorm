package honda.bookworm.tests.Business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.Users.InvalidPasswordException;
import honda.bookworm.Business.Exceptions.Users.InvalidSignupException;
import honda.bookworm.Business.Exceptions.Users.UserNotFoundException;
import honda.bookworm.Business.IAccessUsers;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.Managers.AccessUsers;
import honda.bookworm.Business.Managers.UserManager;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.User;

public class AccessUsersTest {

    private IAccessUsers accessUsers;
    private IUserManager userManager;
    private IUserPersistence userPersistence;

    @Before
    public void setup(){
        System.out.println("Starting for signUpHandler");
        userPersistence = mock(IUserPersistence.class);

        accessUsers = new AccessUsers(userPersistence);
        userManager = new UserManager(userPersistence);
    }

    @Test
    public void testAddUserSuccess(){
        System.out.println("Starting testAddUserSuccess");

        List<User> addedUsers = new ArrayList<>();

        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(new User("hello", "test", "hellotest", "password1"));
        mockUsers.add(new Author("hello", "test", "hellotest2", "password1"));

        when(userPersistence.addUser(mockUsers.get(0))).thenReturn(mockUsers.get(0));
        when(userPersistence.addUser(mockUsers.get(1))).thenReturn(mockUsers.get(1));
        when(userPersistence.getAllUsers()).thenReturn(addedUsers);

        int initialSize = userManager.getAllUsers().size();
        int expectedSize = initialSize + 1;

        //test 1st user, reader variant
        accessUsers.addNewUser("hello", "test", "hellotest", "password1", false);
        addedUsers.add(mockUsers.get(0));
        assertEquals(expectedSize, userManager.getAllUsers().size());

        //test 2nd user, author variant
        accessUsers.addNewUser("hello", "test", "hellotest2", "password1", true);
        addedUsers.add(mockUsers.get(1));
        expectedSize++;
        assertEquals(expectedSize, userManager.getAllUsers().size());

        verify(userPersistence).addUser(mockUsers.get(0));
        verify(userPersistence).addUser(mockUsers.get(1));
        verify(userPersistence, times(3)).getAllUsers();


        System.out.println("Finished testAddUserSuccess");
    }

    @Test
    public void testVerifyUserTrue() {
        System.out.println("Starting testVerifyUserTrue");

        User newUser = new User("jared", "Mandap", "mandapj", "pass123");

        when(userPersistence.addUser(newUser)).thenReturn(newUser);
        when(userPersistence.getUserByUsername("mandapj")).thenReturn(newUser);

        //insert
        userPersistence.addUser(newUser);
        accessUsers.verifyUser("mandapj", "pass123");
        assertEquals(newUser, Services.getActiveUser());

        verify(userPersistence).addUser(newUser);
        verify(userPersistence).getUserByUsername("mandapj");

        System.out.println("Finished testVerifyUserTrue");
    }

    @Test
    public void testVerifyUserFalseNotFound() {
        System.out.println("Starting testVerifyUserFalseNotFound");

        //this user doesn't exist
        String username = "janedoe";
        String password = "password1";

        when(userPersistence.getUserByUsername(username)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> accessUsers.verifyUser(username, password));

        verify(userPersistence).getUserByUsername(username);

        System.out.println("Finished testVerifyUserFalseNotFound");
    }

    @Test
    public void testVerifyUserFalsePasswordWrong(){
        System.out.println("Starting testVerifyUserFalsePasswordWrong");

        User mockUser = new User("john", "doe", "johndoe", "pass");
        //this is the wrong password
        String username = "johndoe";
        String badPassword = "Dumb Password";

        when(userPersistence.getUserByUsername(username)).thenReturn(mockUser);

        assertThrows(InvalidPasswordException.class, () -> accessUsers.verifyUser(username, badPassword));

        verify(userPersistence).getUserByUsername(username);

        System.out.println("Finished testVerifyUserFalsePasswordWrong");
    }

    @Test
    public void testAddUserFailDuplicateUser(){
        System.out.println("Starting testAddUserFailDuplicateUser");

        User newUser = new User("John", "Doe", "johndoe", "password1");

        try {
            User result = accessUsers.addNewUser("John", "Doe", "johndoe", "password1", false);
            assertNotEquals(result, newUser);
        } catch (Exception e){
            System.out.println("Failed to insert user: " + e.getMessage());
            assertTrue(userManager.getAllUsers().contains(newUser)); //check if its actually a duplicate username
        }

        System.out.println("Finished testAddUserFailDuplicateUser");
    }

    @Test
    public void testAddUserFailPasswordBad(){
        System.out.println("Starting testAddUserFailPasswordBad");

        User newUser = new User("Johnathon", "Doe the Third", "johnthe3", "123");

        try{
            assertFalse(userManager.getAllUsers().contains(newUser)); //not inserting a duplicate user
            accessUsers.addNewUser("Johnathon", "Doe the Third", "johnthe3", "123", false);
        } catch(Exception e){
            System.out.println("Failed to insert user: " + e.getMessage());
        }

        assertFalse(userManager.getAllUsers().contains(newUser)); //user still not inserted

        System.out.println("Finished testAddUserFailPasswordBad");
    }

    @Test
    public void testAddUserFailEmptyStrings() {
        System.out.println("Starting testAddUserFailEmptyStrings");

        User newUser = new User("", "", "", "password1");

        try {
            User result = accessUsers.addNewUser("", "", "", "password1", false);
            assertNotNull(result);
            assertEquals(result, newUser);
        } catch (Exception e){
            System.out.println("Failed to insert user: " + e.getMessage());
        }

        //check if user was placed in the stubs
        assertFalse(userManager.getAllUsers().contains(newUser));

        System.out.println("Finished testAddUserFailEmptyStrings");
    }

    @Test
    public void testAddUserFailShortUserName() {
        System.out.println("Starting testAddUserFailShortUserName");

        User newUser = new User("spongebob", "squarepants", "s", "password1");

        try {
            User result = accessUsers.addNewUser("spongebob", "squarepants", "s", "password1", false);
            assertNotNull(result);
            assertEquals(result, newUser);
        } catch (Exception e){
            System.out.println("Failed to insert user: " + e.getMessage());
        }

        //check if user was placed in the stubs
        assertFalse(userManager.getAllUsers().contains(newUser));

        System.out.println("Finished testAddUserFailShortUserName");
    }

    @Test
    public void testValidateUserInput() {
        System.out.println("Starting testValidateUserInput");

        //Test valid input
        try {
            accessUsers.validateUserInput("John", "Doe", "johndoe", "password1");
        } catch (Exception e) {
            fail("Valid input should not throw an exception");
        }

        //Test empty name
        assertThrows(InvalidSignupException.class, () -> accessUsers.validateUserInput("", "Doe", "johndoe", "password1"));


        //Test invalid characters in first name
        try {
            accessUsers.validateUserInput("John123", "Doe", "johndoe", "password1");
            fail("Invalid characters in first name should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test too long first name
        try {
            accessUsers.validateUserInput("Johnathonathonathonathon", "Doe", "johndoe", "password1");
            fail("Too long first name should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test too short first name
        try {
            accessUsers.validateUserInput("J", "Doe", "johndoe", "password1");
            fail("Too short first name should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test empty username
        try {
            accessUsers.validateUserInput("John", "Doe", "", "password1");
            fail("Empty username should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test whitespace in username
        try {
            accessUsers.validateUserInput("John", "Doe", "john doe", "password1");
            fail("Whitespace in username should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test invalid characters in username
        try {
            accessUsers.validateUserInput("John", "Doe", "john@doe", "password1");
            fail("Invalid characters in username should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test too long username
        try {
            accessUsers.validateUserInput("John", "Doe", "johndoejohndoejohndoe", "password1");
            fail("Too long username should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test too short username
        try {
            accessUsers.validateUserInput("John", "Doe", "j", "password1");
            fail("Too short username should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test too short password
        try {
            accessUsers.validateUserInput("John", "Doe", "johndoe", "12");
            fail("Too short password should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test too long password
        try {
            accessUsers.validateUserInput("John", "Doe", "johndoe", "passwordpasswordpassword");
            fail("Too long password should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test whitespace in password
        try {
            accessUsers.validateUserInput("John", "Doe", "johndoe", "password 1");
            fail("Whitespace in password should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test empty password
        try {
            accessUsers.validateUserInput("John", "Doe", "username", "");
            fail("Password can't be empty");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        System.out.println("Finished testValidateUserInput");
    }

    @Test
    public void testFetchUser(){
        System.out.println("Starting testFetchUser");
        final String username = "rowling";
        final String failUsername = "fake";
        final User user = new Author("JK","Rowling", username,"test123");

        when(userPersistence.getUserByUsername(username)).thenReturn(user);
        when(userPersistence.getUserByUsername(failUsername)).thenThrow(UserNotFoundException.class);

        assertEquals(accessUsers.fetchUser(username),user);

        try{
            accessUsers.fetchUser(failUsername);
            fail("Test Failed this line should not run");
        }catch (Exception e){
            assertTrue( e instanceof UserNotFoundException);
        }

        System.out.println("Finished testFetchUser");
    }

    @After
    public void tearDown(){
        userManager.logOutActiveUser();
    }
}
