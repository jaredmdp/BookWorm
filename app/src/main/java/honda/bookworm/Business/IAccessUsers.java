package honda.bookworm.Business;

import honda.bookworm.Object.User;

public interface IAccessUsers {
    User addNewUser(User newUser);
    void verifyUser(String username, String password);
    void validateUserInput(String first, String last, String username, String password);
    User fetchUser (String username);
    String fetchUsernameOfAuthor(int authorID);
}