package honda.bookworm.Object;

import java.util.Objects;
import java.util.ArrayList;

public class Book {
    private String name;
    private String author;
    private int authorID;
    private String ISBN;
    private String description;
    private Genre genre;

    public Book(String name, String author, int authorID, Genre genre, String ISBN) {
        this.name = name;
        this.author = author;
        this.authorID = authorID;
        this.genre = genre;
        this.ISBN = ISBN;
        description = "";
    }

    public Book(String name, String author, int authorID, Genre genre, String ISBN, String description) {
        this.name = name;
        this.author = author;
        this.authorID = authorID;
        this.genre = genre;
        this.ISBN = ISBN;
        this.description = description;
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
                (authorID == book.getAuthorID());
    }
}
