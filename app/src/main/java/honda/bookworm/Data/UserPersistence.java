package honda.bookworm.Data;

import java.util.List;

import honda.bookworm.Object.User;

public interface UserPersistence {
    List<User> getAllUsers();
    User getUserByUsername(String currentUsername);
    User addUser(User currentUser);
    User updateUser(User currentUser, User updateUser);
    User removeUser(User currentUser);


}
