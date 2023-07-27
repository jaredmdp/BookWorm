package honda.bookworm.Object;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private ArrayList<Genre> favoriteGenres;
    private ArrayList<Book> favoriteBooks;

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        favoriteGenres = new ArrayList<>();
        favoriteBooks = new ArrayList<>();
    }

    //Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean canAuthorBooks()
    {
        return false;
    }

    //Getting the Arraylists
    public ArrayList<Book> getFavoriteBooks() {
        return favoriteBooks;
    }

    public ArrayList<Genre> getFavoriteGenres() {
        return favoriteGenres;
    }


    //Comparing, Adding and Removing items, Favorite Genres, and Favorite Books
    public void addToFavoriteGenres(Genre genre) {
        favoriteGenres.add(genre);
    }

    public void removeFromFavoriteGenres(Genre genre) {
        favoriteGenres.remove(genre);
    }

    public boolean isFavouriteGenre(Genre genre) { return favoriteGenres.contains(genre); }

    public void addToFavoriteBooks(Book book) {
        favoriteBooks.add(book);
    }

    public void removeFromFavoriteBooks(Book book) {
        favoriteBooks.remove(book);
    }

    public boolean isFavouriteBook(Book book) { return favoriteBooks.contains(book); }

    public String toString() {
        return "User: " +
                "firstName:'" + firstName + '\'' +
                ", lastName:'" + lastName + '\'' +
                ", username:'" + username + '\'' +
                ", password:'" + password + '\''
                ;
    }

    public boolean equals(Object compare) {
        if (this == compare) {
            return true;
        }

        if (compare == null || getClass() != compare.getClass()) {
            return false;
        }

        User user = (User) compare;

        return Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(favoriteGenres, user.favoriteGenres) &&
                Objects.equals(favoriteBooks, user.favoriteBooks);
    }
}
