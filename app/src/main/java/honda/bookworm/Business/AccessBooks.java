package honda.bookworm.Business;

import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Data.BookPersistence;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

public class AccessBooks {
    private BookPersistence bookPersistence;

    public AccessBooks() {
        bookPersistence = Services.getBookPersistence();
    }

    public List<Book> getBooksGenre(Genre genre) {
        if (genre != null) {
            return bookPersistence.getBooksByGenre(genre);
        } else {
            throw new NullPointerException("genre input can't be null");
        }
    }

}
