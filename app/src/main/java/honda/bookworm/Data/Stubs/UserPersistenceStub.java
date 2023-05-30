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
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getUsername().equalsIgnoreCase(currentUsername)){
                return users.get(i);
            }
        }
        return null;
    }

    @Override
    public User addUser(User currentUser) {
        boolean duplicate = users.contains(currentUser);

        //check duplicate
        if(!duplicate){
            users.add(currentUser);
            return currentUser;
        }
        return null;
    }

    @Override
    public User updateUser(User currentUser, User updateUser) {
        int index = users.indexOf(currentUser);

        //check exist
        if(index >= 0){
            users.set(index,updateUser);
            return updateUser;
        }
        return null;
    }

    @Override
    public User removeUser(User currentUser) {
        int index = users.indexOf(currentUser);

        //check exist
        if(index >= 0){
            users.remove(index);
            return currentUser;
        }
        return null;
    }
}
