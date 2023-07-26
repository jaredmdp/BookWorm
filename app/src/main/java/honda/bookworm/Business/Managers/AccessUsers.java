package honda.bookworm.Business.Managers;
import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.Users.AuthorNotFoundException;
import honda.bookworm.Business.Exceptions.Users.DuplicateUserException;
import honda.bookworm.Business.Exceptions.Users.InvalidPasswordException;
import honda.bookworm.Business.Exceptions.Users.InvalidSignupException;
import honda.bookworm.Business.Exceptions.Users.UserNotFoundException;
import honda.bookworm.Business.IAccessUsers;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.User;
import honda.bookworm.Business.Exceptions.*;

public class AccessUsers implements IAccessUsers {
    private IUserPersistence userPersistence;

    private static final int MAX_LENGTH = 16;
    private static final int MIN_LENGTH = 3;

    public AccessUsers()
    {
        userPersistence = Services.getUserPersistence();
    }

    public AccessUsers(IUserPersistence userPersistence){
        this.userPersistence = userPersistence;
    }

    public User addNewUser(String first, String last, String username, String password, boolean isAuthor) throws DuplicateUserException{
        User newUser;
        User result;

        //form validation checks
        validateUserInput(first, last, username, password);

        if (isAuthor) {
            newUser = new Author(first, last, username, password);
        } else {
            newUser = new User(first, last, username, password);
        }

        try{
            result = userPersistence.addUser(newUser);
            Services.setActiveUser(result);
            return result;
        } catch(GeneralPersistenceException e){
            throw new DuplicateUserException("This username is already used");
        }
    }

    public void verifyUser(String username, String password) throws UserNotFoundException, InvalidPasswordException {
        User user;
        try {
            user = userPersistence.getUserByUsername(username);

            if (user.getPassword().equals(password)) {
                Services.setActiveUser(user);
            } else {
                throw new InvalidPasswordException("Incorrect password provided");
            }
        } catch (GeneralPersistenceException e) {
            e.printStackTrace();
            throw new UserNotFoundException(username);
        }
    }

    public User fetchUser(String username) throws UserNotFoundException{
        User user;

        try{
            user = userPersistence.getUserByUsername(username);
        }catch (GeneralPersistenceException e){
            e.printStackTrace();
            throw new UserNotFoundException(username);
        }

        return user;
    }

    public String fetchUsernameOfAuthor(int authorID) throws AuthorNotFoundException {
        String username;
        try{
            username = userPersistence.getUsernameFromAuthorID(authorID);
        }catch (GeneralPersistenceException e){
            e.printStackTrace();
            throw new AuthorNotFoundException("AuthorID: "+authorID);
        }
     return username;
    }

    //Validator functions----------------------------------------------------------------------------
    public void validateUserInput(String first, String last, String username, String password) throws InvalidSignupException {
        validateName(first);
        validateName(last);
        validateUsername(username);
        validatePassword(password);
    }

    private static void validateName(String name) throws InvalidSignupException {
        if (StringValidator.isEmpty(name)) {
            throw new InvalidSignupException("Name must not be empty");
        }
        if (!StringValidator.isAlphaOnly(name)) {
            throw new InvalidSignupException("Name can only contain alphabets");
        }
        if (StringValidator.isTooLong(name, MAX_LENGTH)) {
            throw new InvalidSignupException("Name cannot exceed "+MAX_LENGTH+" characters");
        }
        if (StringValidator.isTooShort(name, MIN_LENGTH)) {
            throw new InvalidSignupException("Name must be at least "+MIN_LENGTH+" characters");
        }
    }

    private static void validateUsername(String username) throws InvalidSignupException {
        if (StringValidator.isEmpty(username)) {
            throw new InvalidSignupException("Username must not be empty");
        }
        if (StringValidator.containsWhitespace(username)) {
            throw new InvalidSignupException("Username cannot contain spaces");
        }
        if (!StringValidator.isValidInput(username)) {
            throw new InvalidSignupException("Invalid characters used in the username");
        }
        if (StringValidator.isTooLong(username, MAX_LENGTH)) {
            throw new InvalidSignupException("Username cannot exceed "+MAX_LENGTH+" characters");
        }
        if (StringValidator.isTooShort(username, MIN_LENGTH)) {
            throw new InvalidSignupException("Username must be at least "+MIN_LENGTH+" characters");
        }
    }

    private static void validatePassword(String password) throws InvalidSignupException{
        if (StringValidator.isEmpty(password)) {
            throw new InvalidSignupException("Password must not be empty");
        }
        if (StringValidator.isTooLong(password, MAX_LENGTH)) {
            throw new InvalidSignupException("Password cannot exceed "+MAX_LENGTH+" characters");
        }
        if (StringValidator.isTooShort(password, MIN_LENGTH)) {
            throw new InvalidSignupException("Password must be at least "+MIN_LENGTH+" characters");
        }
        if (StringValidator.containsWhitespace(password)) {
            throw new InvalidSignupException("Password cannot contain whitespaces");
        }
    }

}
