package honda.bookworm.Business;

import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

public class AccessBooks {
    private IBookPersistence bookPersistence;
    private final int MAX_BOOK_TITLE_LENGTH = 30;
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

    public String getTrimmedBookName(Book b){
        String bookTitle = b.getName();
        String first,last;

        if(bookTitle.length()> MAX_BOOK_TITLE_LENGTH){
            if(bookTitle.split(" ").length>2) {
                first = bookTitle.substring(0, bookTitle.indexOf(" ", 10));
                last = bookTitle.substring(bookTitle.lastIndexOf(" ") + 1, bookTitle.length());
            }else{
                first = bookTitle.substring(0, 10);
                last = bookTitle.substring(bookTitle.length()-10);
            }
            bookTitle = first+"..."+last;
        }

        return  bookTitle;
    }

}
