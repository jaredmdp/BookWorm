package honda.bookworm.Business;

import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

public interface IAccessBooks {
    Book addBook(String bookTitle, Genre genre, String ISBN, String description, String cover, boolean isPurchaseable);
    
    String getTrimmedBookName(Book book);

    Book getBookByISBN(String isbn);

    void validateBookInput(String bookTitle, String ISBN, String description, String cover);
}