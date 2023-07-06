package honda.bookworm.Data;

import java.util.List;

import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

public interface IBookPersistence {
    List<Book> getAllBooks();
    Book getBookByISBN(String ISBN); //All ISBNS are 13 digits
    Book getBookByTitle(String title);
    Book addBook(Book newBook);
    void removeBookByISBN(String ISBN); //All ISBNS are 13 digits
    void removeBookByTitle(String title);
    List<Book> getBooksByAuthor(String author);
    List<Book> getBooksByGenre(Genre genre);

    boolean isBookFavoriteOfUser(User user, String isbn);
    boolean toggleUserBookFavorite(User user, String isbn);
}
