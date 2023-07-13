package honda.bookworm.tests.Business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import honda.bookworm.Business.Exceptions.Users.InvalidPasswordException;
import honda.bookworm.Business.Exceptions.Users.UserNotFoundException;
import honda.bookworm.Business.Exceptions.Users.InvalidSignupException;
import honda.bookworm.Data.Stubs.UserPersistenceStub;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.User;
import honda.bookworm.Business.IAccessUsers;
import honda.bookworm.Business.Managers.AccessUsers;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.Managers.UserManager;
import honda.bookworm.Application.Services;

public class AccessUsersTest {

    private IAccessUsers signUp;
    private IUserManager manager;
    private IUserPersistence userPersistence;

    @Before
    public void setup(){
        System.out.println("Starting for signUpHandler");

        userPersistence = new UserPersistenceStub();
        signUp = new AccessUsers(userPersistence );
        manager = new UserManager(userPersistence);
    }

    @Test
    public void addNewUser(){
        System.out.println("Starting addNewUser");
        int initialSize = manager.getAllUsers().size();
        int expectedSize = initialSize + 1;

        signUp.addNewUser("hello", "test", "hellotest", "password1", false);

        assertEquals(expectedSize, manager.getAllUsers().size());

        //adds author variant
        signUp.addNewUser("hello", "test", "hellotest2", "password1", true);
        expectedSize = expectedSize + 1;

        assertEquals(expectedSize, manager.getAllUsers().size());

        System.out.println("Finished addNewUser");
    }

    @Test
    public void testVerifyUserTrue() {
        System.out.println("Starting testVerifyUserTrue");

        //exists in stub already
        User newUser = new User("jared", "Mandap", "mandapj", "pass123");

        //insert
        userPersistence.addUser(newUser);
        signUp.verifyUser("mandapj", "pass123");
        assertEquals(newUser, Services.getActiveUser());

        System.out.println("Finished testVerifyUserTrue");
    }

    @Test
    public void testVerifyUserFalseNotFound() {
        System.out.println("Starting testVerifyUserFalseNotFound");

        //this user doesn't exist in Stub
        String username = "janedoe";
        String password = "password1";

        assertThrows(UserNotFoundException.class, () -> signUp.verifyUser(username, password));

        System.out.println("Finished testVerifyUserFalseNotFound");
    }

    @Test
    public void testVerifyUserFalsePasswordWrong(){
        System.out.println("Starting testVerifyUserFalsePasswordWrong");

        //this is the wrong password
        String username = "johndoe";
        String badPassword = "Dumb Password";

        assertThrows(InvalidPasswordException.class, () -> signUp.verifyUser(username, badPassword));

        System.out.println("Finished testVerifyUserFalsePasswordWrong");
    }

    @Test
    public void testAddUserFailDuplicateUser(){
        System.out.println("Starting testAddUserFailDuplicateUser");

        User newUser = new User("John", "Doe", "johndoe", "password1");

        try {
            User result = signUp.addNewUser("John", "Doe", "johndoe", "password1", false);
            assertNotEquals(result, newUser);
        } catch (Exception e){
            System.out.println("Failed to insert user: " + e.getMessage());
            assertTrue(manager.getAllUsers().contains(newUser)); //check if its actually a duplicate username
        }

        System.out.println("Finished testAddUserFailDuplicateUser");
    }

    @Test
    public void testAddUserFailPasswordBad(){
        System.out.println("Starting testAddUserFailPasswordBad");

        User newUser = new User("Johnathon", "Doe the Third", "johnthe3", "123");

        try{
            assertFalse(manager.getAllUsers().contains(newUser)); //not inserting a duplicate user
            signUp.addNewUser("Johnathon", "Doe the Third", "johnthe3", "123", false);
        } catch(Exception e){
            System.out.println("Failed to insert user: " + e.getMessage());
        }

        assertFalse(manager.getAllUsers().contains(newUser)); //user still not inserted

        System.out.println("Finished testAddUserFailPasswordBad");
    }

    @Test
    public void testAddUserSuccess(){
        System.out.println("Starting testAddUserSuccess");

        User newUser = new User("Janey", "Doeee", "janey45", "password1");

        try {
            User result = signUp.addNewUser("Janey", "Doeee", "janey45", "password1", false);
            assertNotNull(result);
            assertEquals(result, newUser);
        } catch (Exception e){
            System.out.println("Failed to insert user: " + e.getMessage());
        }

        //check if user was placed in the stubs
        assertTrue(manager.getAllUsers().contains(newUser));

        System.out.println("Finished testAddUserSuccess");
    }

    @Test
    public void testAddUserFailEmptyStrings() {
        System.out.println("Starting testAddUserFailEmptyStrings");

        User newUser = new User("", "", "", "password1");

        try {
            User result = signUp.addNewUser("", "", "", "password1", false);
            assertNotNull(result);
            assertEquals(result, newUser);
        } catch (Exception e){
            System.out.println("Failed to insert user: " + e.getMessage());
        }

        //check if user was placed in the stubs
        assertFalse(manager.getAllUsers().contains(newUser));

        System.out.println("Finished testAddUserFailEmptyStrings");
    }

    @Test
    public void testAddUserFailShortUserName() {
        System.out.println("Starting testAddUserFailShortUserName");

        User newUser = new User("spongebob", "squarepants", "s", "password1");

        try {
            User result = signUp.addNewUser("spongebob", "squarepants", "s", "password1", false);
            assertNotNull(result);
            assertEquals(result, newUser);
        } catch (Exception e){
            System.out.println("Failed to insert user: " + e.getMessage());
        }

        //check if user was placed in the stubs
        assertFalse(manager.getAllUsers().contains(newUser));

        System.out.println("Finished testAddUserFailShortUserName");
    }

    @Test
    public void testValidateUserInput() {
        System.out.println("Starting testValidateUserInput");

        //Test valid input
        try {
            signUp.validateUserInput("John", "Doe", "johndoe", "password1");
        } catch (Exception e) {
            fail("Valid input should not throw an exception");
        }

        //Test empty name
        assertThrows(InvalidSignupException.class, () -> signUp.validateUserInput("", "Doe", "johndoe", "password1"));


        //Test invalid characters in first name
        try {
            signUp.validateUserInput("John123", "Doe", "johndoe", "password1");
            fail("Invalid characters in first name should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test too long first name
        try {
            signUp.validateUserInput("Johnathonathonathonathon", "Doe", "johndoe", "password1");
            fail("Too long first name should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test too short first name
        try {
            signUp.validateUserInput("J", "Doe", "johndoe", "password1");
            fail("Too short first name should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test empty username
        try {
            signUp.validateUserInput("John", "Doe", "", "password1");
            fail("Empty username should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test whitespace in username
        try {
            signUp.validateUserInput("John", "Doe", "john doe", "password1");
            fail("Whitespace in username should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test invalid characters in username
        try {
            signUp.validateUserInput("John", "Doe", "john@doe", "password1");
            fail("Invalid characters in username should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test too long username
        try {
            signUp.validateUserInput("John", "Doe", "johndoejohndoejohndoe", "password1");
            fail("Too long username should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test too short username
        try {
            signUp.validateUserInput("John", "Doe", "j", "password1");
            fail("Too short username should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test too short password
        try {
            signUp.validateUserInput("John", "Doe", "johndoe", "12");
            fail("Too short password should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test too long password
        try {
            signUp.validateUserInput("John", "Doe", "johndoe", "passwordpasswordpassword");
            fail("Too long password should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test whitespace in password
        try {
            signUp.validateUserInput("John", "Doe", "johndoe", "password 1");
            fail("Whitespace in password should throw an exception");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        //Test empty password
        try {
            signUp.validateUserInput("John", "Doe", "username", "");
            fail("Password can't be empty");
        } catch (Exception e) {
            assertTrue(e instanceof InvalidSignupException);
        }

        System.out.println("Finished testValidateUserInput");
    }

    @After
    public void tearDown(){
        manager.logOutActiveUser();
    }
}
