package honda.bookworm.Data.Stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

import honda.bookworm.Data.UserPersistence;

public class UserPersistenceStub implements UserPersistence {
    private List<User> users;

    public UserPersistenceStub(){
        Author authorBrandon = new Author("Brandon", "Sanderson", "BrandonSanderson", "password4");
        this.users = new ArrayList<>();

        users.add(new User("John", "Doe", "johndoe", "password1"));
        users.add(new User("Jane", "Smith", "janesmith", "password2"));
        users.add(new User("John", "Wick", "johnwick", "password3"));
        users.add(new Author("Joe", "Mama", "joemama", "password4"));
        users.add(authorBrandon);

        authorBrandon.addWrittenBook(new Book("The Way of Kings", "Brandon Sanderson", Genre.Fantasy, "9780765326355"));
        authorBrandon.addWrittenBook(new Book("Mistborn", "Brandon Sanderson", Genre.Fantasy, "9780765350381"));
        authorBrandon.addWrittenBook(new Book("Words of Radiance", "Brandon Sanderson", Genre.Fantasy, "9780765326362"));
        authorBrandon.addWrittenBook(new Book("Elantris", "Brandon Sanderson", Genre.Fantasy, "9780765311788"));
        authorBrandon.addWrittenBook(new Book("The Alloy of Law", "Brandon Sanderson", Genre.Fantasy, "9780765368546"));


    }

    @Override
    public List<User> getAllUsers() {
        return Collections.unmodifiableList(users);
    }

    @Override
    public List<Author> getAllAuthors() {
        List <Author> authors = new ArrayList<>();
        for (int i = 0; i < users.size(); i++){
            if(users.get(i) instanceof  Author){
                authors.add((Author) users.get(i));
            }
        }
        return authors;
    }
    @Override
    public List<Book> getAllWrittenBooks(String authorName){
        List<Book> books = null;
        Author theAuthor;
        String fullName;
        for(int i = 0; i < users.size(); i++){
            fullName = users.get(i).getFirstName() + " " + users.get(i).getLastName();
            if(fullName.equalsIgnoreCase(authorName)){
                theAuthor = (Author) users.get(i);
                books = theAuthor.getWrittenBooks();
            }
        }
        return books;
    }

    @Override
    public User getUserByUsername(String currentUsername) {
        User user = null;
        for(int i = 0; i < users.size() && user==null ; i++){
            if(users.get(i).getUsername().equalsIgnoreCase(currentUsername)){
                user = users.get(i);
            }
        }
        return user;
    }

    @Override
    public User addUser(User currentUser) {
        User result = null;
        String username = currentUser.getUsername();

            if (isDuplicateUsername(username)) {
                throw new IllegalStateException("Username taken.");
            } else {
                users.add(currentUser);
                result = currentUser;
            }
        return result;
    }

    private boolean isDuplicateUsername(String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User updateUser(User currentUser, User updateUser) {
        User result = null;

        try {
            int index = users.indexOf(currentUser);
            if (index != -1) {
                users.set(index, updateUser);
                result = updateUser;
            } else {
                throw new IllegalArgumentException("User not found");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to update user: " + e.getMessage());
        }

        return result;
    }

    @Override
    public User removeUser(User currentUser) {
        int index = users.indexOf(currentUser);

        users.remove(index);
        return currentUser;
    }
}
