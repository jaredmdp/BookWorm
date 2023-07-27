package honda.bookworm.Business.Managers;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.Books.InvalidBookException;
import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
import honda.bookworm.Business.Exceptions.InvalidGenreException;
import honda.bookworm.Business.Exceptions.Users.UserException;
import honda.bookworm.Business.Exceptions.Users.UserNotFoundException;
import honda.bookworm.Business.IUserPreference;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

public class UserPreference implements IUserPreference {
    private IUserPersistence userPersistence;

    private IBookPersistence bookPersistence;

    public UserPreference() {
        userPersistence = Services.getUserPersistence();
        bookPersistence = Services.getBookPersistence();
    }

    public UserPreference(IUserPersistence userPersistence, IBookPersistence bookPersistence){
        this.userPersistence = userPersistence;
        this.bookPersistence = bookPersistence;
    }

    public boolean isGenreFavourite(User user , Genre genre) {
        boolean isFavourite = false;
        try {
            if (user != null && genre!=null) {
                isFavourite = userPersistence.isGenreFavoriteOfUser(user, genre);
            }
        }catch (GeneralPersistenceException e){
            throw new UserException("Unable to determine user favorite genres");
        }
        return isFavourite;
    }

    public boolean genreFavouriteToggle(Genre genre) {
        boolean favState = false;
        try {
            if (Services.getActiveUser() != null && genre!=null) {
                favState = userPersistence.toggleUserGenreFavorite(Services.getActiveUser(), genre);
            }
        } catch (GeneralPersistenceException e){
            throw new UserException("Unable to change user favorite genres");
        }

        return favState;
    }

    public List<Genre> getFavoriteGenreList(User user) {
        List<Genre> genreList = new ArrayList<>();
        if(user != null){
            genreList = userPersistence.getFavoriteGenreList(user);
        }
        return genreList;
    }

    public boolean isBookFavourite(User user , String isbn) {
        boolean isFavourite = false;
        try {
            if (user != null) {
                isFavourite = bookPersistence.isBookFavoriteOfUser(user, isbn);
            }
        }catch (GeneralPersistenceException e){
            throw new UserException("Unable to determine user favorite books");
        }
        return isFavourite;
    }

    public boolean bookFavouriteToggle(String isbn) {
        boolean favState = false;
        try {
            if (Services.getActiveUser() != null) {
                favState = bookPersistence.toggleUserBookFavorite(Services.getActiveUser(), isbn);
            }
        } catch (GeneralPersistenceException e){
            throw new UserException("Unable to change favorite books");
        }

        return favState;
    }

    public List<Book> getFavoriteBookList(User user) {
        List<Book> bookList = new ArrayList<>();
        if(user != null){
            bookList = bookPersistence.getFavoriteBookList(user);
        }
        return bookList;
    }

    @Override
    public List<Book> getBookRecommendations() {
        List<Book> bookList = new ArrayList<>();
        List<Book> userFavoriteBooks;
        User activeUser = Services.getActiveUser();

        try {
            if(activeUser != null) {
                bookList = getBooksByUserFavoriteGenres();
                userFavoriteBooks = getFavoriteBookList(activeUser);

                //make sure the user's favorites are not being recommended to read
                bookList.removeAll(userFavoriteBooks);
            }
            if (activeUser == null || bookList.isEmpty()) {
                bookList = bookPersistence.getMostFavoriteBooks();
            }
        } catch (GeneralPersistenceException e) {
            throw new InvalidBookException("Can't find recommended books");
        }

        //return random 10 books
        Collections.shuffle(bookList);
        int numRecommendations = Math.min(7, bookList.size());
        return bookList.subList(0, numRecommendations);
    }

    private List<Book> getBooksByUserFavoriteGenres() throws InvalidGenreException, GeneralPersistenceException {
        User activeUser = Services.getActiveUser();
        List<Genre> favoriteGenres;

        try {
            favoriteGenres = userPersistence.getFavoriteGenreList(activeUser);
        } catch (GeneralPersistenceException e) {
            throw new InvalidGenreException("Could not get favorite genre list");
        }

        List<Book> booksByUserFavoriteGenres = new ArrayList<>();

        for (Genre favoriteGenre : favoriteGenres) {
            try {
                List<Book> allBooksByGenre = bookPersistence.getBooksByGenre(favoriteGenre);
                booksByUserFavoriteGenres.addAll(allBooksByGenre);
            } catch (GeneralPersistenceException e) {
                throw new InvalidGenreException("Could not get books for genre");
            }
        }

        return booksByUserFavoriteGenres;
    }

}
