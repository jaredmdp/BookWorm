package honda.bookworm.tests.Business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Data.hsqldb.UserPersistenceHSQLDB;
import honda.bookworm.Object.User;
import honda.bookworm.tests.utils.TestUtils;
import honda.bookworm.Business.IAccessUsers;
import honda.bookworm.Business.Managers.AccessUsers;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.Managers.UserManager;

public class AccessUsersIT {
    private IAccessUsers accessUsers;
    private IUserManager manager;
    private File tempDB;

    @Before
    public void setup() throws IOException {
        this.tempDB = TestUtils.copyDB();
        final IUserPersistence persistence = new UserPersistenceHSQLDB(this.tempDB.getAbsolutePath().replace(".script", ""));
        this.accessUsers = new AccessUsers(persistence);
        this.manager = new UserManager(persistence);
    }

    @Test
    public void testGetAllUsers(){
        System.out.println("\nStarting testGetAllUsers");

        accessUsers.addNewUser("John", "Doe", "johndoe", "password1", false);
        accessUsers.addNewUser("John", "Depp", "jondepp", "password2", false);

        List<User> users = manager.getAllUsers();

        assertNotNull(users);
        assertEquals(14, users.size());

        System.out.println("\nFinished testGetAllUsers");

    }

    @Test
    public void testAddNewUser(){
        System.out.println("\nStarting addNewUser");
        int initialSize = manager.getAllUsers().size();
        int expectedSize = initialSize + 1;

        accessUsers.addNewUser("hello", "test", "hellotest", "password1", false);

        assertEquals(expectedSize, manager.getAllUsers().size());

        //adds author variant
        accessUsers.addNewUser("hello", "test", "hellotest2", "password1", true);
        expectedSize = expectedSize + 1;

        assertEquals(expectedSize, manager.getAllUsers().size());

        System.out.println("\nFinished addNewUser");
    }

    @Test
    public void testAddDupeUser() {
        System.out.println("\nStarting testAddDupeUser");

        int initialSize = manager.getAllUsers().size();
        int expectedSize = initialSize + 1;
        accessUsers.addNewUser("hello", "test", "hellotest", "password1", false);
        assertEquals(expectedSize, manager.getAllUsers().size());

        try {
            //insert same user
            accessUsers.addNewUser("hello", "test", "hellotest", "password1", false);
            assertFalse(true);
        } catch (RuntimeException e) {
            System.out.println("Success: Duplicate user not inserted");
        }

        System.out.println("\nFinished testAddDupeUser");
    }

    @Test
    public void testVerifyUserTrue() {
        System.out.println("\nStarting testVerifyUserTrue");

        accessUsers.addNewUser("John", "Doe", "johndoe", "password1", false);
        String username = "johndoe";
        String password = "password1";

        boolean result = accessUsers.verifyUser(username, password);

        assertTrue(result);

        System.out.println("\nFinished testVerifyUserTrue");
    }

    @Test
    public void testVerifyUserFalseNotFound() {
        System.out.println("Starting testVerifyUserFalseNotFound");

        //this user doesn't exist in DB
        String username = "janedoe";
        String password = "password1";

        try {
            boolean result = accessUsers.verifyUser(username, password);
            assertFalse(result);
        } catch (Exception e) {
            System.out.println("Failed to verify user: " + e.getMessage());
        }

        System.out.println("Finished testVerifyUserFalseNotFound");
    }

    @Test
    public void testVerifyUserFalsePasswordWrong(){
        System.out.println("Starting testVerifyUserFalsePasswordWrong");

        //add user
        accessUsers.addNewUser("John", "Doe", "johndoe", "password1", false);

        //this is the wrong password
        String username = "johndoe";
        String badPassword = "Dumb Password";

        try{
            boolean result = accessUsers.verifyUser(username, badPassword);
            assertFalse(result);
        } catch(Exception e){
            System.out.println("Failed to verify user: " + e.getMessage());
        }

        System.out.println("Finished testVerifyUserFalsePasswordWrong");
    }

    @Test
    public void testAddUserFailDuplicateUser(){
        System.out.println("Starting testAddUserFailDuplicateUser");

        //insert user to DB
        User newUser =  accessUsers.addNewUser("John", "Doe", "johndoe", "password1", false);

        try {
            User result = accessUsers.addNewUser("John", "Doe", "johndoe", "password1", false);
            assertNotEquals(result, newUser);
        } catch (Exception e){
            System.out.println("Failed to insert user: " + e.getMessage());
            assertTrue(manager.getAllUsers().contains(newUser)); //check if its actually a duplicate username
        }

        System.out.println("Finished testAddUserFailDuplicateUser");
    }

    @After
    public void tearDown(){
        this.tempDB.delete();
    }
}
