package honda.bookworm.tests.Business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import honda.bookworm.Business.Exceptions.Users.AuthorNotFoundException;
import honda.bookworm.Business.Exceptions.Users.DuplicateUserException;
import honda.bookworm.Business.Exceptions.Users.InvalidPasswordException;
import honda.bookworm.Business.Exceptions.Users.UserException;
import honda.bookworm.Business.Exceptions.Users.UserNotFoundException;
import honda.bookworm.Business.IAccessUsers;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Business.Managers.AccessUsers;
import honda.bookworm.Business.Managers.UserManager;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Data.hsqldb.UserPersistenceHSQLDB;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.User;
import honda.bookworm.tests.utils.TestUtils;

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

        accessUsers.addNewUser(new User("John", "Doe", "johndoe", "password1"));
        accessUsers.addNewUser(new User("John", "Depp", "jondepp", "password2"));

        List<User> users = manager.getAllUsers();

        assertNotNull(users);
        assertEquals(16, users.size());

        System.out.println("\nFinished testGetAllUsers");

    }

    @Test
    public void testAddNewUser(){
        System.out.println("\nStarting addNewUser");
        int initialSize = manager.getAllUsers().size();
        int expectedSize = initialSize + 1;

        accessUsers.addNewUser(new User("hello", "test", "hellotest", "password1"));

        assertEquals(expectedSize, manager.getAllUsers().size());

        //adds author variant
        accessUsers.addNewUser(new Author("hello", "test", "hellotest2", "password1"));
        expectedSize = expectedSize + 1;

        assertEquals(expectedSize, manager.getAllUsers().size());

        System.out.println("\nFinished addNewUser");
    }

    @Test
    public void testAddDupeUser() {
        System.out.println("\nStarting testAddDupeUser");

        int initialSize = manager.getAllUsers().size();
        int expectedSize = initialSize + 1;
        accessUsers.addNewUser(new User("hello", "test", "hellotest", "password1"));
        assertEquals(expectedSize, manager.getAllUsers().size());

        try {
            //insert same user
            accessUsers.addNewUser(new User("hello", "test", "hellotest", "password1"));
            fail();
        } catch (DuplicateUserException e) {
            System.out.println("Error: Duplicate user: " + e.getMessage());
        }

        System.out.println("\nFinished testAddDupeUser");
    }

    @Test
    public void testVerifyUserTrue() {
        System.out.println("\nStarting testVerifyUserTrue");

        accessUsers.addNewUser(new User("John", "Doe", "johndoe", "password1"));
        String username = "johndoe";
        String password = "password1";

        accessUsers.verifyUser(username, password);

        System.out.println("\nFinished testVerifyUserTrue");
    }

    @Test
    public void testVerifyUserFalseNotFound() {
        System.out.println("Starting testVerifyUserFalseNotFound");

        // This user doesn't exist in the DB
        String username = "janedoe";
        String password = "password1";

        assertThrows(UserNotFoundException.class, () -> accessUsers.verifyUser(username, password));

        System.out.println("Finished testVerifyUserFalseNotFound");
    }

    @Test
    public void testVerifyUserFalsePasswordWrong(){
        System.out.println("Starting testVerifyUserFalsePasswordWrong");

        //add user
        accessUsers.addNewUser(new User("John", "Doe", "johndoe", "password1"));

        //this is the wrong password
        String username = "johndoe";
        String badPassword = "Dumb Password";

        assertThrows(InvalidPasswordException.class, () -> accessUsers.verifyUser(username, badPassword));

        System.out.println("Finished testVerifyUserFalsePasswordWrong");
    }

    @Test
    public void testAddUserFailDuplicateUser(){
        System.out.println("Starting testAddUserFailDuplicateUser");

        //insert user to DB
        User newUser =  accessUsers.addNewUser(new User("John", "Doe", "johndoe", "password1"));

        try {
            User result = accessUsers.addNewUser(new User("John", "Doe", "johndoe", "password1"));
            assertNotEquals(result, newUser);
        } catch (DuplicateUserException e){
            System.out.println("Failed to insert user: " + e.getMessage());
            assertTrue(manager.getAllUsers().contains(newUser)); //check if its actually a duplicate username
        }

        System.out.println("Finished testAddUserFailDuplicateUser");
    }

    @Test
    public void testFetchValidUser(){
        System.out.println("Starting testFetchValidUser");
        final String username = "rowling";
        try{
            User result = accessUsers.fetchUser(username);
            assertNotNull(result);
            assertEquals(username,result.getUsername());
        }catch (UserException e){
            fail("Must not fail to fetch: "+e.getMessage());
        }
        System.out.println("Finished testFetchValidUser");
    }

    @Test
    public void testFetchInvalidUser(){
        System.out.println("Starting testFetchInvalidUser");
        final String username = "mustFail";
        try{
            User result = accessUsers.fetchUser(username);
            fail("test failed this should not be called");
        }catch (UserException e){
            assertTrue( e instanceof  UserNotFoundException);
        }
        System.out.println("Finished testFetchInvalidUser");
    }

    @Test
    public void testfetchUsernameOfAuthorValid(){
        System.out.println("Starting testfetchUsernameOfAuthorValid");
        final String username = "rowling";
        final int id = 8;
        try{
            String result = accessUsers.fetchUsernameOfAuthor(id);
            assertEquals(result,username);
        }catch (Exception e){
            fail("No exceptions should be thrown");
        }
        System.out.println("Finished testfetchUsernameOfAuthorValid");
    }

    @Test
    public void testfetchUsernameOfAuthorInvalid(){
        System.out.println("Starting testfetchUsernameOfAuthorInvalid");
        final int over9000 = 9001;
        try{
            accessUsers.fetchUsernameOfAuthor(over9000);
            fail("Exception should have been thrown");
        }catch (Exception e){
           assert (e instanceof AuthorNotFoundException);
        }

        System.out.println("Finished testfetchUsernameOfAuthorInvalid");
    }

    @After
    public void tearDown(){
        manager.logOutActiveUser();
        this.tempDB.delete();
    }
}
