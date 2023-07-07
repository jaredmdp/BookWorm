package honda.bookworm.Business.Managers;


import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.Users.UserNotFoundException;
import honda.bookworm.Business.IUserPreference;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

public class UserPreference implements IUserPreference {
    private IUserPersistence userPersistence;

    public UserPreference() {
        userPersistence = Services.getUserPersistence(true);
    }

    public UserPreference(IUserPersistence userPersistence){
        this.userPersistence = userPersistence;
    }


    public boolean isGenreFavourite(User user , Genre genre) {
        boolean isFavourite = false;
        try {
            if (user != null && genre!=null) {
                isFavourite = userPersistence.isGenreFavoriteOfUser(user, genre);
            }
        }catch (UserNotFoundException e){
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
        } catch (UserNotFoundException e){
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

}
