package honda.bookworm.Business;

import java.util.List;

import honda.bookworm.Object.Book;

public interface ISearchManager {
    List<Book> performSearchGenre(String query);
    List<Book> performSearchAuthor(String query);
    List<Book> performSearchISBN(String ISBN);
    List<Book> performSearchTitle(String title);
}
