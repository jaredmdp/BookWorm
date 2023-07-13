package honda.bookworm.Business;

import java.util.List;

import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

public interface IAccessBooks {
    Book addBook(String bookTitle, Genre genre, String ISBN, String description, String cover, boolean isPurchaseable);
    
    String getTrimmedBookName(Book book);
    public List<Book> getAuthorIDBookList (int authorID);

    Book getBookByISBN(String isbn);

    void validateBookInput(String bookTitle, String ISBN, String description,Genre genre, String cover);
}