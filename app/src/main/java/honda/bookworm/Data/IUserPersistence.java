package honda.bookworm.Data;

import java.util.List;

import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

public interface IUserPersistence {
    List<User> getAllUsers();
    User getUserByUsername(String currentUsername);
    User addUser(User currentUser);
    boolean isGenreFavoriteOfUser(User user, Genre genre);
    boolean toggleUserGenreFavorite(User user, Genre genre);
    List<Genre> getFavoriteGenreList(User user);
}
