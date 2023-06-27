package honda.bookworm.Business.Managers;
import honda.bookworm.Application.Services;
import honda.bookworm.Business.IAccessUsers;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.User;

public class AccessUsers implements IAccessUsers {
    private IUserPersistence userPersistence;

    public AccessUsers()
    {
        userPersistence = Services.getUserPersistence(true);
    }

    public AccessUsers(IUserPersistence userPersistence){
        this.userPersistence = userPersistence;
    }

    public User addNewUser(String first, String last, String username, String password, boolean isAuthor){
        User newUser;
        User result;

        //form validation checks
        validateUserInput(first, last, username, password);

        if (isAuthor) {
            newUser = new Author(first, last, username, password, 0);
        } else {
            newUser = new User(first, last, username, password);
        }

        result = userPersistence.addUser(newUser);

        if(result == null){
            throw new IllegalStateException("Username taken.");
        }

        Services.setActiveUser(result);

        return result;
    }

    public boolean verifyUser(String username, String password) {
        User user = userPersistence.getUserByUsername(username);

        if (user != null) {
            if( user.getPassword().equals(password) ){
                Services.setActiveUser(user);
                return true;
            }else {
                throw new IllegalStateException("Incorrect password provided.");
            }
        } else {
            throw new IllegalStateException(String.format("User '%s' not found",username));
        }
    }

    //Validator functions----------------------------------------------------------------------------
    public void validateUserInput(String first, String last, String username, String password) {
        validateName(first);
        validateName(last);
        validateUsername(username);
        validatePassword(password);
    }

    public static void validateName(String name) {
        if (StringValidator.isEmpty(name)) {
            throw new IllegalStateException("Name must not be empty");
        }
        if (!StringValidator.isAlphaOnly(name)) {
            throw new IllegalStateException("Name can only contain alphabets");
        }
        if (StringValidator.isTooLong(name)) {
            throw new IllegalStateException("Name cannot exceed 16 characters");
        }
        if (StringValidator.isTooShort(name)) {
            throw new IllegalStateException("Name must be greater than 2 characters");
        }
    }

    public static void validateUsername(String username) {
        if (StringValidator.isEmpty(username)) {
            throw new IllegalStateException("Username must not be empty");
        }
        if (StringValidator.containsWhitespace(username)) {
            throw new IllegalStateException("Username cannot contain spaces");
        }
        if (!StringValidator.isValidInput(username)) {
            throw new IllegalStateException("Invalid characters used in the username");
        }
        if (StringValidator.isTooLong(username)) {
            throw new IllegalStateException("Username cannot be greater than 16 characters");
        }
        if (StringValidator.isTooShort(username)) {
            throw new IllegalStateException("Username must be greater than 2 characters");
        }
    }

    public static void validatePassword(String password) {
        if (StringValidator.isTooShort(password)) {
            throw new IllegalStateException("Password must be greater than 2 characters");
        }
        if (StringValidator.isTooLong(password)) {
            throw new IllegalStateException("Password must be less than 16 characters");
        }
        if (StringValidator.containsWhitespace(password)) {
            throw new IllegalStateException("Password cannot contain whitespaces");
        }
    }

}
