package honda.bookworm.Application;

import honda.bookworm.Data.BookPersistence;
import honda.bookworm.Data.Stubs.BookPersistenceStub;
import honda.bookworm.Data.Stubs.UserPersistenceStub;
import honda.bookworm.Data.UserPersistence;
import honda.bookworm.Object.User;

public class Services {
    private static BookPersistence bookPersistence = null;
    private static UserPersistence userPersistence = null;

    private static User activeUser = null;

    public static synchronized BookPersistence getBookPersistence(){
        if (bookPersistence == null){
            bookPersistence = new BookPersistenceStub();
        }
        return bookPersistence;
    }

    public static synchronized UserPersistence getUserPersistence(){
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
