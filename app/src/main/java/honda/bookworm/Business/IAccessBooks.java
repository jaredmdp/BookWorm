package honda.bookworm.Business;

import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

import java.util.List;

public interface IAccessBooks {
    List<Book> getBooksGenre(Genre genre);
    Book addBook(Book newBook);
    String getTrimmedBookName(Book book);
}