package honda.bookworm.Data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

public class BookPersistenceHSQLDB implements IBookPersistence {

    private final String dbPath;

    public BookPersistenceHSQLDB(final String dbPath){
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public List<Book> getAllBooks() {
        return null;
    }

    @Override
    public Book getBookByISBN(String ISBN) {
        return null;
    }

    @Override
    public Book getBookByTitle(String title) {
        return null;
    }

    @Override
    public Book addBook(Book newBook) {
        try (final Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement("INSERT INTO books VALUES(?, ?, ?, ?, ?)");
            statement.setString(1, newBook.getName());
            statement.setString(2, newBook.getAuthor());
            statement.setString(3, newBook.getGenreAsString());
            statement.setString(4, newBook.getISBN());
            statement.setString(5, newBook.getDescription());

            statement.executeUpdate();

            return newBook;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeBookByISBN(String ISBN) {

    }

    @Override
    public void removeBookByTitle(String title) {

    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return null;
    }

    @Override
    public List<Book> getBooksByGenre(Genre genre) {
        List<Book> booksByGenre = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement ("SELECT * FROM books where genre = ?");
            statement.setString(1, genre.toString());

            final ResultSet result = statement.executeQuery();
            while (result.next()){
                final Book book = fromResultSet(result);
                booksByGenre.add(book);
            }

            statement.close();
            result.close();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return booksByGenre;
    }

    private Book fromResultSet(final ResultSet result) throws SQLException{
        final String bookName = result.getString("bookname");
        final String bookAuthor = result.getString("authorname");
        final Genre bookGenre = Genre.valueOf(result.getString("genre"));
        final String ISBN = result.getString("ISBN");
        final String description = result.getString("description");

        return new Book(bookName,bookAuthor,bookGenre,ISBN, description);

    }
}
