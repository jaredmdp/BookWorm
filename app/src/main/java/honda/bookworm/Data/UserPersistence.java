package honda.bookworm.Data;

import java.util.List;

import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.User;

public interface UserPersistence {
    List<User> getAllUsers();
    List<Author> getAllAuthors();
    List<Book> getAllWrittenBooks(String author);
    User getUserByUsername(String currentUsername);
    User addUser(User currentUser);
    User updateUser(User currentUser, User updateUser);
    User removeUser(User currentUser);


}
