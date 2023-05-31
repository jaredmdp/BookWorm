package honda.bookworm.Data.Stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import honda.bookworm.Object.User;

import honda.bookworm.Data.UserPersistence;

public class UserPersistenceStub implements UserPersistence {
    private List<User> users;
    public UserPersistenceStub(){
        this.users = new ArrayList<>();

        users.add(new User("John", "Doe", "johndoe", "password1"));
        users.add(new User("Jane", "Smith", "janesmith", "password2"));
        users.add(new User("John", "Wick", "johnwick", "password3"));
        users.add(new User("Joe", "Mama", "joemama", "password4"));
    }

    @Override
    public List<User> getAllUsers() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public User getUserByUsername(String currentUsername) {
        User user = null;
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUsername().equalsIgnoreCase(currentUsername)){
                user = users.get(i);
            }
        }
        return user;
    }

    @Override
    public User addUser(User currentUser) {
        users.add(currentUser);
        return currentUser;
    }

    @Override
    public User updateUser(User currentUser, User updateUser) {
        int index = users.indexOf(currentUser);

        users.set(index,updateUser);
        return updateUser;

    }

    @Override
    public User removeUser(User currentUser) {
        int index = users.indexOf(currentUser);

        users.remove(index);
        return currentUser;
    }
}
