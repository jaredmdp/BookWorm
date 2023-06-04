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
    public void testVerifyUserFalse() {
        System.out.println("Starting testVerifyUserFalse");

        //this user doesn't exist in Stub
        String username = "janedoe";
        String password = "password1";

        try {
            boolean result = signUp.verifyUser(username, password);
            assertFalse(result);
        } catch (Exception e) {
            System.out.println("Failed to update user: " + e.getMessage());
        }

        System.out.println("Finished testVerifyUserFalse");
    }

    @Test
    public void testAddUserFail(){
        System.out.println("Starting testAddUserFail");

        User newUser = new User("John", "Doe", "johndoe", "password1");

        try {
            User result = signUp.addNewUser("John", "Doe", "johndoe", "password1", false);
            assertNotEquals(result, newUser);
        } catch (Exception e){
            System.out.println("Failed to insert user: " + e.getMessage());
            assertTrue(signUp.getAllUser().contains(newUser)); //check if its actually a duplicate username
        }

        System.out.println("Finished testAddUserFail");
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


}
