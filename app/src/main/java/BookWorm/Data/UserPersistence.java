package BookWorm.Data;

import java.util.List;

import BookWorm.Object.User;

public interface UserPersistence {
    List<User> getAllUsers();
    User getUserByUsername(String currentUsername);
    User addUser(User currentUser);
    User updateUser(User currentUser, User updateUser);
    User removeUser(User currentUser);


}
