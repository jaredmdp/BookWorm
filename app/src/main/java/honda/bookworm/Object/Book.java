package honda.bookworm.Object;

import java.util.Objects;
import java.util.ArrayList;

public class Book {
    private String name;
    private String author;
    private String ISBN;
    private ArrayList<Genre> genre;

    public Book(String name, String author, Genre genre, String ISBN) {
        this.name = name;
        this.author = author;
        this.genre = new ArrayList<Genre>();
        this.genre.add(genre);
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

    public ArrayList<Genre> getGenre() {
        return genre;
    }

    public void addGenre(Genre genre) {this.genre.add(genre);}

    public void removeGenre(Genre genre){this.genre.remove(genre);}

    public String toString() {
        //create string list of genres for output
        String genres = "";
        for(int i=0; i<this.genre.size()-1; i++) {
            genres = genres + this.genre.get(i).toString() + " ";
        }
        genres = genres + this.genre.get(this.genre.size()-1).toString();

        return "Book name:'" + name + '\'' +
                ", author:'" + author + '\'' +
                ", genre:'" + genres + '\'' +
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
