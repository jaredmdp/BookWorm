package honda.bookworm.Data.Stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import honda.bookworm.Business.Exceptions.Users.DuplicateUserException;
import honda.bookworm.Business.Exceptions.Users.UserNotFoundException;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

import honda.bookworm.Data.IUserPersistence;

public class UserPersistenceStub implements IUserPersistence {
    private List<User> users;

    public UserPersistenceStub(){
        Author authorBrandon = new Author("Brandon", "Sanderson", "BrandonSanderson", "password4", 0);
        this.users = new ArrayList<>();

        users.add(new User("John", "Doe", "johndoe", "password1"));
        users.add(new User("Jane", "Smith", "janesmith", "password2"));
        users.add(new User("John", "Wick", "johnwick", "password3"));
        users.add(new Author("Joe", "Mama", "joemama", "password4", 1));
        users.add(authorBrandon);

        authorBrandon.addWrittenBook(new Book("The Way of Kings", "Brandon Sanderson", 0, Genre.Fantasy, "9780765326355"));
        authorBrandon.addWrittenBook(new Book("Mistborn", "Brandon Sanderson", 0, Genre.Fantasy, "9780765350381"));
        authorBrandon.addWrittenBook(new Book("Words of Radiance", "Brandon Sanderson", 0, Genre.Fantasy, "9780765326362"));
        authorBrandon.addWrittenBook(new Book("Elantris", "Brandon Sanderson", 0, Genre.Fantasy, "9780765311788"));
        authorBrandon.addWrittenBook(new Book("The Alloy of Law", "Brandon Sanderson", 0, Genre.Fantasy, "9780765368546"));


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

        if (user == null) {
            throw new UserNotFoundException("Invalid username");
        }

        return user;
    }

    @Override
    public User addUser(User currentUser) {
        String username = currentUser.getUsername();

            if (isDuplicateUsername(username)) {
                throw new DuplicateUserException("Username " + username +  " is taken");
            } else {
                users.add(currentUser);
                return currentUser;
            }
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
    public User removeUser(User currentUser) {
        int index = users.indexOf(currentUser);

        users.remove(index);
        return currentUser;
    }

    @Override
    public boolean isGenreFavoriteOfUser(User user, Genre genre){
        return user!=null && genre!=null && user.isFavouriteGenre(genre);
    }

    @Override
    public boolean toggleUserGenreFavorite(User user, Genre genre){
        boolean isFav = isGenreFavoriteOfUser(user,genre);

        if(user!=null && genre!=null) {
            if(isFav){
                isFav = false;
                user.removeFromFavoriteGenres(genre);
            }else {
                isFav = true;
                user.addToFavoriteGenres(genre);
            }
        }

        return isFav;
    }

}
