package honda.bookworm.Data;

import java.util.List;

import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

public interface IBookPersistence {
    Book getBookByISBN(String ISBN); //All ISBNS are 13 digits
    List<Book> searchBooksByISBN (String query);
    List<Book> getBooksByTitle(String title);
    Book addBook(Book newBook);
    List<Book> getBooksByAuthor(String author);
    List<Book> getBooksByGenre(Genre genre);
    public List<Book> getBooksByAuthorID(int authorID);

    boolean isBookFavoriteOfUser(User user, String isbn);
    boolean toggleUserBookFavorite(User user, String isbn);

    List<Book> getFavoriteBookList(User user);
}
