package honda.bookworm.Business.Managers;

import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.Books.BookException;
import honda.bookworm.Business.Exceptions.Books.DuplicateISBNException;
import honda.bookworm.Business.Exceptions.Books.InvalidBookException;
import honda.bookworm.Business.Exceptions.Books.InvalidISBNException;
import honda.bookworm.Business.Exceptions.Users.AuthorNotFoundException;
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

    public AccessBooks() {bookPersistence = Services.getBookPersistence();}

    public AccessBooks(IBookPersistence bookPersistence){
        this.bookPersistence = bookPersistence;
    }

    public Book addBook(String bookTitle, Genre genre, String ISBN, String description, String cover, boolean isPurchaseable)
            throws DuplicateISBNException, InvalidBookException, IllegalStateException, InvalidGenreException {
        User current = Services.getActiveUser();

        if(!(current.canAuthorBooks())) {
            throw new IllegalStateException("Only Authors can insert books");
        }

        Author author = (Author) current;

        validateBookInput(bookTitle, ISBN, description, genre, cover);

        Book newBook = new Book(bookTitle, author.getFirstName()+" "+author.getLastName(), author.getAuthorID(),
                genre, ISBN, description, cover, isPurchaseable);

        return bookPersistence.addBook(newBook);
    }

    @Override
    public List<Book> getAuthorIDBookList (int authorID) throws BookException {
        List<Book> bookList = new ArrayList<>();

        validateAuthorID(authorID);

        try {
            bookList = bookPersistence.getBooksByAuthorID(authorID);
        } catch (GeneralPersistenceException e) {
            throw new BookException("Could not get written books for this author");
        }

        return bookList;
    }

    @Override
    public Book getBookByISBN(String isbn) throws InvalidISBNException {
        Book book = null;

        try{
            book = bookPersistence.getBookByISBN(isbn);
        }catch (GeneralPersistenceException e){
            throw new InvalidISBNException("Could not get item. "+e.getMessage());
        }

        return book;
    }

    public List<Genre> getAllAvailableGenres() {
        List<Genre> availableGenres;

        try {
            availableGenres = bookPersistence.getAllAvailableGenreList();
        }catch (GeneralPersistenceException gpe){
            availableGenres = new ArrayList<>();
        }

        return availableGenres;
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

    //Validator functions----------------------------------------------------------------------------
    private void validateBookInput(String bookTitle, String ISBN, String description,Genre genre, String cover){
        validateBookTitle(bookTitle);
        validateISBN(ISBN);
        validateDescription(description);
        validateCover(cover);
        validateGenre(genre);
    }

    private static void validateBookTitle(String bookTitle) throws InvalidBookException{
        if(StringValidator.isTooLong(bookTitle, MAX_TITLE_LENGTH)){
            throw new InvalidBookException("Title cannot exceed "+MAX_TITLE_LENGTH+" characters");
        }
        if(StringValidator.isTooShort(bookTitle, MIN_TITLE_LENGTH)){
            throw new InvalidBookException("Title must be at least "+MIN_TITLE_LENGTH+" characters");
        }
    }

    private static void validateISBN(String ISBN) throws InvalidBookException{
        if(!(StringValidator.isNumericOnly(ISBN))){
            throw new InvalidBookException("ISBN codes can only contain numbers");
        }
        if(!(StringValidator.isExactly(ISBN, ISBN_LENGTH))){
            throw new InvalidBookException("ISBN codes should be exactly "+ISBN_LENGTH+" characters");
        }
    }

    private static void validateDescription(String description) throws InvalidBookException{
        if(StringValidator.isTooLong(description, MAX_DESCRIPTION_LENGTH)){
            throw new InvalidBookException("Description cannot exceed "+MAX_DESCRIPTION_LENGTH+" characters");
        }
        if(StringValidator.isTooShort(description, MIN_DESCRIPTION_LENGTH)){
            throw new InvalidBookException("Description must be at least "+MIN_DESCRIPTION_LENGTH+" characters");
        }
    }

    private static void validateCover(String cover) throws InvalidBookException{
        if(cover == null){
            throw new InvalidBookException("Error with book cover");
        }
    }

    private static void validateAuthorID (int authorID) throws AuthorNotFoundException {
        if (authorID < 0) {
            throw new AuthorNotFoundException("Invalid Author ID: " + authorID);
        }
    }

    private static void validateGenre(Genre genre) throws InvalidGenreException{
        if (genre == null)
            throw new InvalidGenreException("A valid genre must be selected");
    }

}
