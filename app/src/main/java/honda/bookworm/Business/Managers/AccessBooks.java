package honda.bookworm.Business.Managers;

import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.Books.DuplicateISBNException;
import honda.bookworm.Business.Exceptions.Books.InvalidBookException;
import honda.bookworm.Business.Exceptions.Books.InvalidISBNException;
import honda.bookworm.Business.Exceptions.Users.UserNotFoundException;
import honda.bookworm.Business.IAccessBooks;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Business.Exceptions.*;
import honda.bookworm.Object.User;

public class AccessBooks implements IAccessBooks {
    private IBookPersistence bookPersistence;
    private final int MAX_BOOK_TITLE_LENGTH = 40;

    public AccessBooks() {
        bookPersistence = Services.getBookPersistence(true);
    }

    public AccessBooks(IBookPersistence bookPersistence){
        this.bookPersistence = bookPersistence;
    }

    //TODO We have to change Exception handling once addBook Parameters change and connected with UI. Ask Jared
    public Book addBook(Book newBook) throws DuplicateISBNException, InvalidBookException {
        if (newBook == null) {
            throw new InvalidBookException("Book can't be empty");
        }
          return bookPersistence.addBook(newBook);
    }

    public boolean isBookFavourite(User user , String isbn) {
        boolean isFavourite = false;
        try {
            if (user != null) {
                isFavourite = bookPersistence.isBookFavoriteOfUser(user, isbn);
            }
        }catch (UserNotFoundException e){
            e.printStackTrace();
        }
        return isFavourite;
    }

    public boolean bookFavouriteToggle(String isbn) {
        boolean favState = false;
        try {
            if (Services.getActiveUser() != null) {
                favState = bookPersistence.toggleUserBookFavorite(Services.getActiveUser(), isbn);
            }
        } catch (UserNotFoundException e){
            e.printStackTrace();
        }

        return favState;
    }

    @Override
    public Book getBookByISBN(String isbn) throws InvalidISBNException {
        Book book = null;

        try{
            book = bookPersistence.getBookByISBN(isbn);
        }catch (GeneralPersistenceException e){
            e.printStackTrace();
        }

        return book;
    }

    public List<Book> getFavoriteBookList(User user) {
        List<Book> bookList = new ArrayList<>();
        if(user != null){
            bookList = bookPersistence.getFavoriteBookList(user);
        }
        return bookList;
    }


    public String getTrimmedBookName(Book b) {
        String trimmedTitle = b.getName().trim();
        String [] words;

        if (trimmedTitle.length() > MAX_BOOK_TITLE_LENGTH) {
            words = trimmedTitle.split(" ");
            if(words.length>1) {
                trimmedTitle = titleTrimHelper(words);
            }else {
                trimmedTitle= titleTrimmer(trimmedTitle,"");
            }
        }

        return trimmedTitle;
    }

    private String titleTrimHelper(String [] words){
        String first = words[0];
        String middle = " ";
        String last = words [words.length-1];
        int i = 1;

        while((first+middle+last).length()< MAX_BOOK_TITLE_LENGTH && i < words.length-1){
            middle= middle+words[i]+" ";
            i++;
        }

        return titleTrimmer(first+middle,last);
    }

    private String titleTrimmer(String first, String last) {
        StringBuilder trimmedTitle = new StringBuilder();
        int firstLength = first.length();
        int lastLength = last.length();

        if (last.isEmpty()) {
            trimmedTitle.append(first, 0, (firstLength / 2));
            trimmedTitle.append("...");
            trimmedTitle.append(first, (int) (firstLength * 0.85), firstLength);
        } else if (firstLength < lastLength) {
            trimmedTitle.append(first.trim());
            trimmedTitle.append("...");
            trimmedTitle.append(last, (int) (lastLength * 0.85), lastLength);
        } else {
            trimmedTitle.append(first, 0, (int) (firstLength * 0.55));
            trimmedTitle.append("...");
            trimmedTitle.append(last);
        }

        return trimmedTitle.toString();
    }

}
