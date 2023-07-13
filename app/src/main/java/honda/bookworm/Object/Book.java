package honda.bookworm.Object;

import java.util.Arrays;
import java.util.Objects;

public class Book {
    private String name;
    private String author;
    private int authorID;
    private String ISBN;
    private String description;
    private Genre genre;
    private String cover;
    private boolean isPurchaseable;

    public Book(String name, String author, int authorID, Genre genre, String ISBN) {
        this.name = name;
        this.author = author;
        this.authorID = authorID;
        this.genre = genre;
        this.ISBN = ISBN;
        description = "";
        cover = "";
        isPurchaseable = false;
    }

    public Book(String name, String author, int authorID, Genre genre, String ISBN, String description) {
        this.name = name;
        this.author = author;
        this.authorID = authorID;
        this.genre = genre;
        this.ISBN = ISBN;
        this.description = description;
        cover = "";
        isPurchaseable = false;
    }

    public Book(String name, String author, int authorID, Genre genre, String ISBN, String description, String cover) {
        this.name = name;
        this.author = author;
        this.authorID = authorID;
        this.genre = genre;
        this.ISBN = ISBN;
        this.description = description;
        this.cover = cover;
        isPurchaseable = false;
    }

    public Book(String name, String author, int authorID, Genre genre, String ISBN, String description, String cover, boolean isPurchaseable) {
        this.name = name;
        this.author = author;
        this.authorID = authorID;
        this.genre = genre;
        this.ISBN = ISBN;
        this.description = description;
        this.cover = cover;
        this. isPurchaseable = isPurchaseable;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAuthorID() {return authorID;}

    public void setAuthorID(int authorID) {this.authorID = authorID;}

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {this.genre = genre;}

    public void setCover(String cover) {this.cover = cover;}

    public void setPurchaseable(boolean canPurchase){this.isPurchaseable = canPurchase;}

    public boolean getPurchaseable(){return isPurchaseable;}

    public String getCover() {return cover;}

    public String toString() {
        return "Book name:'" + name + '\'' +
                ", author:'" + author + '\'' +
                ", genre:'" + genre + '\'' +
                ", ISBN:'" + ISBN + '\''
                ;
    }

    public boolean equals(Object compare) {
        if (this == compare) {
            return true;
        }

        if (compare == null || getClass() != compare.getClass()) {
            return false;
        }

        Book book = (Book) compare;

        return  Objects.equals(name, book.name) &&
                Objects.equals(author, book.author) &&
                Objects.equals(genre, book.genre) &&
                Objects.equals(ISBN, book.ISBN) &&
                Objects.equals(cover, book.cover) &&
                (authorID == book.getAuthorID()) &&
                isPurchaseable == book.getPurchaseable();
    }
}
