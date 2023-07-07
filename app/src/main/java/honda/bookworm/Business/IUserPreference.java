package honda.bookworm.Business;

import java.util.List;

import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

public interface IUserPreference {
    boolean isGenreFavourite(User u, Genre genre);
    List<Genre> getFavoriteGenreList(User user);

    boolean genreFavouriteToggle(Genre genre);
}