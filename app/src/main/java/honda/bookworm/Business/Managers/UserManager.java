package honda.bookworm.Business.Managers;

import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.IUserManager;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.User;

public class UserManager implements IUserManager {

    private IUserPersistence userPersistence;

    public UserManager()
    {
        userPersistence = Services.getUserPersistence();
    }

    public UserManager(IUserPersistence userPersistence){
        this.userPersistence = userPersistence;
    }

    public boolean isAuthorActive(){
        return getActiveUser() instanceof Author;
    }

    public User getActiveUser(){
        return Services.getActiveUser();
    }
    public void logOutActiveUser(){
        Services.setActiveUser(null);
    }
    public boolean isUserLoggedIn(){return Services.getActiveUser()!=null;}
    public List<User> getAllUsers(){
        return userPersistence.getAllUsers();
    }
}
