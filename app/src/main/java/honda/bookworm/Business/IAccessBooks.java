package honda.bookworm.Business;

import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

import java.util.List;

public interface IAccessBooks {
    List<Book> getBooksGenre(Genre genre);
    Book addBook(Book newBook);
    String getTrimmedBookName(Book book);

    boolean isBookFavourite(User u, String isbn);

    //favoriteToggle should only be accessed by active user
    boolean bookFavouriteToggle(String isbn);
    Book getBookByISBN(String isbn);
}