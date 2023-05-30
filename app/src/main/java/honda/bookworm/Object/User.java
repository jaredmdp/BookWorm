package honda.bookworm.Object;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private ArrayList<Book> wishlist;
    private ArrayList<String> favoriteGenres;
    private ArrayList<Book> favoriteBooks;

    public User(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        wishlist = new ArrayList<>();
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

    //Getting the Arraylists
    public ArrayList<Book> getWishlist() {
        return wishlist;
    }

    public ArrayList<Book> getFavoriteBooks() {
        return favoriteBooks;
    }

    public ArrayList<String> getFavoriteGenres() {
        return favoriteGenres;
    }

    //Adding and Removing items from Wishlist, Favorite Genres, and Favorite Books
    public void addToWishlist(Book book) {
        wishlist.add(book);
    }

    public void removeFromWishlist(Book book) {
        wishlist.remove(book);
    }

    public void addToFavoriteGenres(String genre) {
        favoriteGenres.add(genre);
    }

    public void removeFromFavoriteGenres(String genre) {
        favoriteGenres.remove(genre);
    }

    public void addToFavoriteBooks(Book book) {
        favoriteBooks.add(book);
    }

    public void removeFromFavoriteBooks(Book book) {
        favoriteBooks.remove(book);
    }

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
                Objects.equals(wishlist, user.wishlist) &&
                Objects.equals(favoriteGenres, user.favoriteGenres) &&
                Objects.equals(favoriteBooks, user.favoriteBooks);
    }
}
