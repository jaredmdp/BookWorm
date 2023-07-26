package honda.bookworm.Object.Factories;

import honda.bookworm.Business.Exceptions.Users.UserException;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.User;

public class UserFactory implements IUserFactory{
    public User make(String first, String last, String username, String password, String type) throws UserException {
        if(type.equalsIgnoreCase("user")) { return new User(first, last, username, password); }
        else if(type.equalsIgnoreCase("author")) { return new Author(first, last, username, password); }
        else { throw new UserException("Account type selection error, try again"); }
    }
}
