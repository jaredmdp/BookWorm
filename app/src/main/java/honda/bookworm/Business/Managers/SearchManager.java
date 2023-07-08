package honda.bookworm.Business.Managers;

import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
import honda.bookworm.Business.Exceptions.InvalidSearchException;
import honda.bookworm.Business.ISearchManager;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

public class SearchManager implements ISearchManager {
    private IBookPersistence bookPersistence;

    public SearchManager() {
        bookPersistence = Services.getBookPersistence(true);
    }

    public SearchManager(IBookPersistence bookPersistence){
        this.bookPersistence = bookPersistence;
    }

    public List<Book> performSearchGenre(String query) throws InvalidSearchException {
        List<Book> result = new ArrayList<>();

        Genre genre = validateGenre(query);

        try {
            if (genre != null) {
                result = bookPersistence.getBooksByGenre(genre);
            }
        } catch (GeneralPersistenceException e) {
            e.printStackTrace();
            throw new InvalidSearchException("Invalid genre");
        }

        return result;
    }

    public List<Book> performSearchAuthor(String query) throws InvalidSearchException {
        List<Book> result;

        validateAuthorName(query);

        try {
            result = bookPersistence.getBooksByAuthor(query);
        } catch (GeneralPersistenceException e) {
            e.printStackTrace();
            throw new InvalidSearchException("Could not find author");
        }

        return result;
    }

    public List<Book> performSearchISBN (String query) throws InvalidSearchException {
        List <Book> result;

        validateISBN(query);

        try {
            result = bookPersistence.searchBooksByISBN(query);
        } catch (GeneralPersistenceException e) {
            e.printStackTrace();
            throw new InvalidSearchException("Invalid ISBN:" + query);
        }

        return result;
    }

    public List<Book> performSearchTitle (String query) throws InvalidSearchException {
        List <Book> result;

        try {
            result = bookPersistence.getBooksByTitle(query);
        } catch (GeneralPersistenceException e) {
            throw new InvalidSearchException("Invalid Title: " + query);
        }

        return result;
    }

    //Validator functions----------------------------------------------------------------------------
    private Genre validateGenre(String query) throws InvalidSearchException {
        if (!StringValidator.isAlphaDashes(query)) {
            throw new InvalidSearchException("Genre can only contain alphabets: " + query);
        }

        String lowerCaseQuery = query.toLowerCase();

        for (Genre genre : Genre.values()) {
            String lowerCaseGenre = genre.toString().toLowerCase();

            if (lowerCaseGenre.equals(lowerCaseQuery)) {
                return genre;
            }
        }

        return null;
    }

    private void validateISBN(String query) throws InvalidSearchException {
        if (!StringValidator.isNumericOnly(query)) {
            throw new InvalidSearchException("ISBN can only contain numeric values: " + query);
        }
    }

    private void validateAuthorName (String query) throws InvalidSearchException {
        if (!StringValidator.isValidName(query)) {
            throw new InvalidSearchException("Author names or usernames may only contain alphabets and numbers" + query);
        }
    }
}
