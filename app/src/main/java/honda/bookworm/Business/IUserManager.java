package honda.bookworm.Business;

import honda.bookworm.Object.User;

import java.util.List;

public interface IUserManager {
    User getActiveUser();
    boolean isAuthorActive();
    void logOutActiveUser();
    List<User> getAllUsers();
    boolean isUserLoggedIn();
}