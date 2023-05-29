package BookWorm.Object;

import java.util.Objects;

public class Book {
    private String name;
    private String author;
    private String ISBN;
    private String genre;

    public Book(String name, String author, String genre, String ISBN) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.ISBN = ISBN;
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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

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
                Objects.equals(ISBN, book.ISBN)
                ;
    }
}
