package honda.bookworm.Data.Stubs;

import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Data.BookPersistence;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

public class BookPersistenceStub implements BookPersistence {
    private List<Book> books;

    public BookPersistenceStub() {
        this.books = new ArrayList<>();

        books.add(new Book("The Way of Kings", "Brandon Sanderson", Genre.Fantasy, "9780765326355"));
        books.add(new Book("Mistborn", "Brandon Sanderson", Genre.Fantasy, "9780765350381"));
        books.add(new Book("Words of Radiance", "Brandon Sanderson", Genre.Fantasy, "9780765326362"));
        books.add(new Book("Elantris", "Brandon Sanderson", Genre.Fantasy, "9780765311788"));
        books.add(new Book("The Alloy of Law", "Brandon Sanderson", Genre.Fantasy, "9780765368546"));

        books.add(new Book ("The Last Wish","Andrzej Sapkowski", Genre.Adult,"0575077832") );
        books.add(new Book ("Blood of Elves","Andrzej Sapkowski", Genre.Adult,"9780316029193") );
        books.add(new Book ("Sword of Destiny","Andrzej Sapkowski", Genre.Adult,"970575077832") );
        books.add(new Book ("The Time of Contempt","Andrzej Sapkowski", Genre.Adult,"0316219134") );

        books.add(new Book ("The Fake Book","Jouttun Tall", Genre.Fiction,"12345678909") );
        books.add(new Book ("Totally Fake Book","Vanier Myth", Genre.Fiction,"92345678909") );
        books.add(new Book ("Not a Fake Book","Vanier Myth", Genre.Fiction,"192345668809") );
        books.add(new Book ("Again to the Past","Nurse Brown", Genre.Fiction,"192345768809") );
        books.add(new Book ("Again to the Past Once More","Nurse Brown", Genre.Fiction,"192342768809") );
        books.add(new Book ("It Depends!","Who Noels", Genre.Fiction,"992342968809") );

        books.add(new Book ("The Wager","David Grann",Genre.Survival ,"292345768801") );
        books.add(new Book ("Life of Pi","Yann Martel", Genre.Survival,"292344468801") );
        books.add(new Book ("The Martian","Andy Weir", Genre.Survival,"292344464801") );
        books.add(new Book ("Lord of the Flies","William Golding", Genre.Survival,"292346664801") );
        books.add(new Book ("The Maze Runner","James Dasher", Genre.Survival,"292346964801") );
        books.add(new Book ("On the Island","Tracy Gravis Graves", Genre.Survival,"29234555801") );

        books.add(new Book ("Clean Code","Robert C. Martin", Genre.NonFiction,"9780132350884") );
        books.add(new Book ("Atomic Habits","James Clear", Genre.NonFiction,"5780100220888") );
        books.add(new Book ("The Art of War","Sun Tzu", Genre.NonFiction,"578099920888") );
        books.add(new Book ("Introduction to Algorithms","Thomas H. Cormen", Genre.NonFiction,"9780132350884") );
        books.add(new Book ("The C Programming Language","Brian W. Kernighan", Genre.NonFiction,"9780132650884") );
        books.add(new Book ("Operating Systems: Three Easy Pieces","Remzi H. Arpaci-Dusseau", Genre.NonFiction,"9780100650884") );

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
        Book result = null;

        try {
            if (isDuplicateISBN(newBook.getISBN())) {
                throw new IllegalArgumentException("Duplicate ISBN found");
            } else if (isDuplicateTitle(newBook.getName())) {
                throw new IllegalArgumentException("Duplicate title found");
            } else {
                books.add(newBook);
                result = newBook;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to add book: " + e.getMessage());
        }

        return result;
    }

    private boolean isDuplicateISBN(String ISBN) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getISBN().equals(ISBN)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDuplicateTitle(String title) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getName().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
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

    @Override
    public List<Book> getBooksByGenre(Genre genre) {
        List<Book> booksByGenre = new ArrayList<>();

        for (Book book : books){
            for (Genre bookGenre : book.getGenre() ){
                if(genre == bookGenre){
                    booksByGenre.add(book);
                }
            }
        }

        if (booksByGenre.isEmpty()) {
            return null;
        }

        return booksByGenre;
    }


}
