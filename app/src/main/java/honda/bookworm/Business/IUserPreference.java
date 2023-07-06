package honda.bookworm.Business;

import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

public interface IUserPreference {
    boolean isGenreFavourite(User u, Genre genre);

    //favoriteToggle should only be accessed by active user
    boolean genreFavouriteToggle(Genre genre);
}