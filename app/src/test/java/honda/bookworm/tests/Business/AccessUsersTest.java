package honda.bookworm.tests.Business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import honda.bookworm.Business.AccessUsers;

import honda.bookworm.Object.User;

public class AccessUsersTest {

    private AccessUsers signUp;
    @Before
    public void setup(){
        System.out.println("Starting for signUpHandler");
        signUp = new AccessUsers();
    }

    @Test
    public void addNewUser(){
        System.out.println("Starting addNewUser");
        int initialSize = signUp.getAllUser().size();
        int expectedSize = initialSize + 1;

        signUp.addNewUser("hello", "test", "hellotest", "password1", false);

        assertEquals(expectedSize, signUp.getAllUser().size());

        //adds author variant
        signUp.addNewUser("hello", "test", "hellotest2", "password1", true);
        expectedSize = expectedSize + 1;

        assertEquals(expectedSize, signUp.getAllUser().size());

        System.out.println("Finished addNewUser");
    }

    @Test
    public void testVerifyUserTrue() {
        System.out.println("Starting testVerifyUserTrue");

        //exists in stub already
        String username = "johndoe";
        String password = "password1";

        boolean result = signUp.verifyUser(username, password);

        assertTrue(result);

        System.out.println("Finished testVerifyUserTrue");
    }

    @Test
    public void testVerifyUserFalseNotFound() {
        System.out.println("Starting testVerifyUserFalseNotFound");

        //this user doesn't exist in Stub
        String username = "janedoe";
        String password = "password1";

        try {
            boolean result = signUp.verifyUser(username, password);
            assertFalse(result);
        } catch (Exception e) {
            System.out.println("Failed to verify user: " + e.getMessage());
        }

        System.out.println("Finished testVerifyUserFalseNotFound");
    }

    @Test
    public void testVerifyUserFalsePasswordWrong(){
        System.out.println("Starting testVerifyUserFalsePasswordWrong");

        //this is the wrong password
        String username = "johndoe";
        String badPassword = "Dumb Password";

        try{
            boolean result = signUp.verifyUser(username, badPassword);
            assertFalse(result);
        } catch(Exception e){
            System.out.println("Failed to verify user: " + e.getMessage());
        }

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
            assertTrue(signUp.getAllUser().contains(newUser)); //check if its actually a duplicate username
        }

        System.out.println("Finished testAddUserFailDuplicateUser");
    }

    @Test
    public void testAddUserFailPasswordBad(){
        System.out.println("Starting testAddUserFailPasswordBad");

        User newUser = new User("Johnathon", "Doe the Third", "johnthe3", "123");

        try{
            assertFalse(signUp.getAllUser().contains(newUser)); //not inserting a duplicate user
            User result = signUp.addNewUser("Johnathon", "Doe the Third", "johnthe3", "123", false);
        } catch(Exception e){
            System.out.println("Failed to insert user: " + e.getMessage());
        }

        assertFalse(signUp.getAllUser().contains(newUser)); //user still not inserted

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
        assertTrue(signUp.getAllUser().contains(newUser));

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
        assertFalse(signUp.getAllUser().contains(newUser));

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
        assertFalse(signUp.getAllUser().contains(newUser));

        System.out.println("Finished testAddUserFailShortUserName");
    }

    @Test
    public void testValidateUserInput() {
        System.out.println("Starting testValidateUserInput");

        //Test valid input
        try {
            signUp.validateUserInput("John", "Doe", "johndoe", "password1");
        } catch (Exception e) {
            assertTrue("Valid input should not throw an exception", false);
        }

        //Test empty first name
        try {
            signUp.validateUserInput("", "Doe", "johndoe", "password1");
            assertTrue("Empty first name should throw an exception", false);
        } catch (Exception e) {
            assertEquals("First name and last name must not be empty", e.getMessage());
        }

        //Test invalid characters in first name
        try {
            signUp.validateUserInput("John123", "Doe", "johndoe", "password1");
            assertTrue("Invalid characters in first name should throw an exception", false);
        } catch (Exception e) {
            assertEquals("First name and last name can only contain alphabets", e.getMessage());
        }

        //Test too long first name
        try {
            signUp.validateUserInput("Johnathonathonathonathon", "Doe", "johndoe", "password1");
            assertTrue("Too long first name should throw an exception", false);
        } catch (Exception e) {
            assertEquals("First name and last name cannot exceed 16 characters", e.getMessage());
        }

        //Test too short first name
        try {
            signUp.validateUserInput("J", "Doe", "johndoe", "password1");
            assertTrue("Too short first name should throw an exception", false);
        } catch (Exception e) {
            assertEquals("First name and last name must be greater than 2 characters", e.getMessage());
        }

        //Test empty username
        try {
            signUp.validateUserInput("John", "Doe", "", "password1");
            assertTrue("Empty username should throw an exception", false);
        } catch (Exception e) {
            assertEquals("Username must not be empty", e.getMessage());
        }

        //Test whitespace in username
        try {
            signUp.validateUserInput("John", "Doe", "john doe", "password1");
            assertTrue("Whitespace in username should throw an exception", false);
        } catch (Exception e) {
            assertEquals("Username cannot contain spaces", e.getMessage());
        }

        //Test invalid characters in username
        try {
            signUp.validateUserInput("John", "Doe", "john@doe", "password1");
            assertTrue("Invalid characters in username should throw an exception", false);
        } catch (Exception e) {
            assertEquals("Invalid characters used in the username", e.getMessage());
        }

        //Test too long username
        try {
            signUp.validateUserInput("John", "Doe", "johndoejohndoejohndoe", "password1");
            assertTrue("Too long username should throw an exception", false);
        } catch (Exception e) {
            assertEquals("Username cannot be greater than 16 characters", e.getMessage());
        }

        //Test too short username
        try {
            signUp.validateUserInput("John", "Doe", "j", "password1");
            assertTrue("Too short username should throw an exception", false);
        } catch (Exception e) {
            assertEquals("Username must be greater than 2 characters", e.getMessage());
        }

        //Test too short password
        try {
            signUp.validateUserInput("John", "Doe", "johndoe", "12");
            assertTrue("Too short password should throw an exception", false);
        } catch (Exception e) {
            assertEquals("Password must be greater than 2 characters", e.getMessage());
        }

        //Test too long password
        try {
            signUp.validateUserInput("John", "Doe", "johndoe", "passwordpasswordpassword");
            assertTrue("Too long password should throw an exception", false);
        } catch (Exception e) {
            assertEquals("Password must be less than 16 characters", e.getMessage());
        }

        //Test whitespace in password
        try {
            signUp.validateUserInput("John", "Doe", "johndoe", "password 1");
            assertTrue("Whitespace in password should throw an exception", false);
        } catch (Exception e) {
            assertEquals("Password cannot contain whitespaces", e.getMessage());
        }

        System.out.println("Finished testValidateUserInput");
    }


}
