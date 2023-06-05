package honda.bookworm.Business;

import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Data.UserPersistence;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.User;

public class AccessUsers {

    private UserPersistence userPersistence;

    public AccessUsers(){
        userPersistence = Services.getUserPersistence();
    }

    public User addNewUser(String first, String last, String username, String password, boolean isAuthor){
        User newUser;
        User result;

        //form validation checks
        validateInput(first, last, username, password);

        if (isAuthor) {
            newUser = new Author(first, last, username, password);
        } else {
            newUser = new User(first, last, username, password);
        }

        result = userPersistence.addUser(newUser);

        if(result != null){
            Services.setActiveUser(result);
        }

        return result;
    }

    private void validateInput(String first, String last, String username, String password) {
        if (isEmpty(first) || isEmpty(last) || isEmpty(username)) {
            throw new IllegalStateException("First name, last name, and username must not be empty");
        }

        if (password.length() <= 3) {
            throw new IllegalStateException("Password must be greater than 3 characters");
        }

        if (username.length() < 2) {
            throw new IllegalStateException("Usernames must be at least 2 characters long.");
        }
    }

    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
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


}
