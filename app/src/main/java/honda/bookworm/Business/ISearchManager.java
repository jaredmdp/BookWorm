package honda.bookworm.Business;

import java.util.List;

import honda.bookworm.Object.Book;
import honda.bookworm.Object.User;

public interface ISearchManager {
    List<Book> performSearchGenre(String query);
    List<Book> performSearchAuthor(String query);
    List<Book> performSearchISBN(String ISBN);
    List<Book> performSearchTitle(String title);
    List<User> performSearchUser(String query);
}
