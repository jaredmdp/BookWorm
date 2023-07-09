package honda.bookworm.Business;

import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

import java.util.List;

public interface IAccessBooks {
    Book addBook(String bookTitle, Genre genre, String ISBN, String description, String cover);
    String getTrimmedBookName(Book book);

    boolean isBookFavourite(User u, String isbn);

    //favoriteToggle should only be accessed by active user
    boolean bookFavouriteToggle(String isbn);

    Book getBookByISBN(String isbn);

    List<Book> getFavoriteBookList(User user);
    
    void validateBookInput(String bookTitle, String ISBN, String description, String cover);
}