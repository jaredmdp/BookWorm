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
import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Business.Exceptions.*;
import honda.bookworm.Object.User;

public class AccessBooks implements IAccessBooks {
    private IBookPersistence bookPersistence;
    private static final int MAX_TITLE_TRIM_LENGTH = 40;
    private static final int MAX_TITLE_LENGTH = 45;
    private static final int MIN_TITLE_LENGTH = 1;
    private static final int MAX_DESCRIPTION_LENGTH = 5000;
    private static final int MIN_DESCRIPTION_LENGTH = 1;
    private static final int ISBN_LENGTH = 13;

    public AccessBooks() {bookPersistence = Services.getBookPersistence(true);}

    public AccessBooks(IBookPersistence bookPersistence){
        this.bookPersistence = bookPersistence;
    }

    //TODO We have to change Exception handling once addBook Parameters change and connected with UI. Ask Jared
    public Book addBook(String bookTitle, Genre genre, String ISBN, String description, String cover)
            throws DuplicateISBNException, InvalidBookException, IllegalStateException {
        User current = Services.getActiveUser();
        if(!(current instanceof Author)) {
            throw new IllegalStateException("Only Authors can insert books");
        }
        Author author = (Author) current;

        validateBookInput(bookTitle, ISBN, description, cover);

        Book newBook = new Book(bookTitle, author.getFirstName()+" "+author.getLastName(), author.getAuthorID(),
                genre, ISBN, description, cover);

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

        if (trimmedTitle.length() > MAX_TITLE_TRIM_LENGTH) {
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

        while((first+middle+last).length()< MAX_TITLE_TRIM_LENGTH && i < words.length-1){
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

    public void validateBookInput(String bookTitle, String ISBN, String description, String cover){
        validateBookTitle(bookTitle);
        validateISBN(ISBN);
        validateDescription(description);
        validateCover(cover);
    }

    private static void validateBookTitle(String bookTitle){
        if(StringValidator.isTooLong(bookTitle, MAX_TITLE_LENGTH)){
            throw new InvalidBookException("Title cannot exceed "+MAX_DESCRIPTION_LENGTH+" characters");
        }
        if(StringValidator.isTooShort(bookTitle, MIN_TITLE_LENGTH)){
            throw new InvalidBookException("Title must be at least "+MIN_DESCRIPTION_LENGTH+" characters");
        }
    }

    private static void validateISBN(String ISBN){
        if(!(StringValidator.isNumericOnly(ISBN))){
            throw new InvalidBookException("ISBN codes can only contain numbers");
        }
        if(!(StringValidator.isExactly(ISBN, ISBN_LENGTH))){
            throw new InvalidBookException("ISBN codes should be exactly "+ISBN_LENGTH+" characters");
        }
    }

    private static void validateDescription(String description){
        if(StringValidator.isTooLong(description, MAX_DESCRIPTION_LENGTH)){
            throw new InvalidBookException("Description cannot exceed "+MAX_DESCRIPTION_LENGTH+" characters");
        }
        if(StringValidator.isTooShort(description, MIN_DESCRIPTION_LENGTH)){
            throw new InvalidBookException("Description must be at least "+MIN_DESCRIPTION_LENGTH+" characters");
        }
    }

    private static void validateCover(String cover){
        if(cover == null){
            throw new InvalidBookException("Error with book cover");
        }
    }

}
