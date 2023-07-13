package honda.bookworm.Business;

import honda.bookworm.Object.User;

public interface IAccessUsers {
    User addNewUser(String first, String last, String username, String password, boolean isAuthor);

    void verifyUser(String username, String password);
    void validateUserInput(String first, String last, String username, String password);
}