package honda.bookworm.Business;

import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.User;

public class AccessUsers {
    private IUserPersistence userPersistence;

    public AccessUsers(){
        userPersistence = Services.getUserPersistence();
    }

    public User addNewUser(String first, String last, String username, String password, boolean isAuthor){
        User newUser;
        User result;

        //form validation checks
        validateUserInput(first, last, username, password);

        if (isAuthor) {
            newUser = new Author(first, last, username, password);
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

    public User getActiveUser(){
        return Services.getActiveUser();
    }
    public void logOutActiveUser(){
        Services.setActiveUser(null);
    }

    public List<User> getAllUser(){
        return userPersistence.getAllUsers();
    }

    //Validator functions----------------------------------------------------------------------------
    public void validateUserInput(String first, String last, String username, String password) {
        validateNames(first, last);
        validateUsername(username);
        validatePassword(password);
    }
    public static void validateNames(String first, String last) {
        if (StringValidator.isEmpty(first) || StringValidator.isEmpty(last)) {
            throw new IllegalStateException("First name and last name must not be empty");
        }
        if (!StringValidator.isAlphaOnly(first) || !StringValidator.isAlphaOnly(last)) {
            throw new IllegalStateException("First name and last name can only contain alphabets");
        }
        if (StringValidator.isTooLong(first) || StringValidator.isTooLong(last)) {
            throw new IllegalStateException("First name and last name cannot exceed 16 characters");
        }
        if (StringValidator.isTooShort(first) || StringValidator.isTooShort(last)) {
            throw new IllegalStateException("First name and last name must be greater than 2 characters");
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
