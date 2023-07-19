package honda.bookworm.Application;

import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Data.ICommentPersistence;
import honda.bookworm.Data.hsqldb.BookPersistenceHSQLDB;
import honda.bookworm.Data.hsqldb.CommentPersistenceHSQLDB;
import honda.bookworm.Data.hsqldb.UserPersistenceHSQLDB;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.User;

public class Services {
    private static IBookPersistence bookPersistence = null;
    private static IUserPersistence userPersistence = null;
    private static ICommentPersistence commentPersistence = null;
    private static User activeUser = null;
    
    public static synchronized IBookPersistence getBookPersistence(){
        if (bookPersistence == null){
            bookPersistence = new BookPersistenceHSQLDB(Main.getDBPathName());
        }
        return bookPersistence;
    }

    public static synchronized IUserPersistence getUserPersistence(){
        if (userPersistence == null){
            userPersistence = new UserPersistenceHSQLDB(Main.getDBPathName());
        }
        return userPersistence;
    }

    public static synchronized ICommentPersistence getCommentPersistence(){
        if(commentPersistence == null){
            commentPersistence = new CommentPersistenceHSQLDB(Main.getDBPathName());
        }

        return commentPersistence;
    }

    public static User getActiveUser(){
        return activeUser;
    }

    public static void setActiveUser(User thisUser){
        activeUser = thisUser;
    }
}
