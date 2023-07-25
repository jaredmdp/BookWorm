package honda.bookworm.Data;

import java.util.List;

import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

public interface IUserPersistence {
    void removeUser(String username);
    List<User> getAllUsers();
    User getUserByUsername(String currentUsername);
    User addUser(User currentUser);
    boolean isGenreFavoriteOfUser(User user, Genre genre);
    boolean toggleUserGenreFavorite(User user, Genre genre);
    List<Genre> getFavoriteGenreList(User user);
    String getUsernameFromAuthorID(int authorID);
    List<User> searchUsersByQuery(String query);
}
