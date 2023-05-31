package honda.bookworm.Data.Stubs;

import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Data.BookPersistence;
import honda.bookworm.Object.Book;

public class BookPersistenceStub implements BookPersistence {
    private List<Book> books;

    public BookPersistenceStub() {
        this.books = new ArrayList<>();

        books.add(new Book("The Way of Kings", "Brandon Sanderson", "Fantasy Fiction", "9780765326355"));
        books.add(new Book("Mistborn", "Brandon Sanderson", "Fantasy Fiction", "9780765350381"));
        books.add(new Book("Words of Radiance", "Brandon Sanderson", "Fantasy Fiction", "9780765326362"));
        books.add(new Book("Elantris", "Brandon Sanderson", "Fantasy Fiction", "9780765311788"));
        books.add(new Book("The Alloy of Law", "Brandon Sanderson", "Fantasy Fiction", "9780765368546"));
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    @Override
    public Book getBookByISBN(String ISBN) {
        for(int i = 0; i < books.size(); i++){
            if(books.get(i).getISBN().equals(ISBN)){
                return books.get(i);
            }
        }
        return null;
    }

    @Override
    public Book getBookByTitle(String title) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getName().equalsIgnoreCase(title)) {
                return books.get(i);
            }
        }
        return null;
    }

    @Override
    public Book addBook(Book newBook) {
        books.add(newBook);
        return newBook;
    }

    @Override
    public void removeBookByISBN(String ISBN) {

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getISBN().equals(ISBN)) {
                    books.remove(i);
            }
        }
    }

    @Override
    public void removeBookByTitle(String Title) {

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getName().equalsIgnoreCase(Title)) {
                books.remove(i);
            }
        }
    }

    //Create list of books by author by traversing through booklist
    //Currently implemented as String. Need to change to Author author after changes are made to Object
    @Override
    public List<Book> getBooksByAuthor(String author) {
        List<Book> booksByAuthor = new ArrayList<>();

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getAuthor().equalsIgnoreCase(author)) {
                booksByAuthor.add(books.get(i));
            }
        }

        if (booksByAuthor.isEmpty()) {
            return null;
        }

        return booksByAuthor;
    }

}
