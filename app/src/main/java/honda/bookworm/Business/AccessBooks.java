package honda.bookworm.Business;

import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Data.BookPersistence;
import honda.bookworm.Data.Stubs.BookPersistenceStub;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

public class AccessBooks {
    private BookPersistence bookPersistence;

    public AccessBooks(){
        bookPersistence = Services.getBookPersistence();
    }

    public List<Book> getBooksGenre(Genre getGenre){
        List <Book> booksGenre = null;
        if(getGenre != null){
            booksGenre = bookPersistence.getBooksByGenre(getGenre);
        }
        return booksGenre;
    }
}
