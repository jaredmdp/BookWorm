package honda.bookworm.Business.Managers;


import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
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
            e.printStackTrace();
        }
        return isFavourite;
    }

    public boolean genreFavouriteToggle(Genre genre) {
        boolean favState = false;
        try {
            if (Services.getActiveUser() != null) {
                favState = userPersistence.toggleUserGenreFavorite(Services.getActiveUser(), genre);
            }
        } catch (GeneralPersistenceException e){
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return isFavourite;
    }

    public boolean bookFavouriteToggle(String isbn) {
        boolean favState = false;
        try {
            if (Services.getActiveUser() != null && isbn != null) {
                favState = bookPersistence.toggleUserBookFavorite(Services.getActiveUser(), isbn);
            }
        } catch (GeneralPersistenceException e){
            e.printStackTrace();
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

}
