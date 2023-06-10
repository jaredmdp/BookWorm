package honda.bookworm.Application;

import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Data.Stubs.BookPersistenceStub;
import honda.bookworm.Data.Stubs.UserPersistenceStub;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.User;

public class Services {
    private static IBookPersistence bookPersistence = null;
    private static IUserPersistence userPersistence = null;

    private static User activeUser = null;

    public static synchronized IBookPersistence getBookPersistence(){
        if (bookPersistence == null){
            bookPersistence = new BookPersistenceStub();
        }
        return bookPersistence;
    }

    public static synchronized IUserPersistence getUserPersistence(){
        if (userPersistence == null){
            userPersistence = new UserPersistenceStub();
        }
        return userPersistence;
    }

    public static User getActiveUser(){
        return activeUser;
    }

    public static void setActiveUser(User thisUser){
        activeUser = thisUser;
    }
}
