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
import honda.bookworm.Business.Exceptions.Books.*;
import honda.bookworm.Business.Exceptions.InvalidGenreException;
import honda.bookworm.Business.Exceptions.GeneralPersistenceException;

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
            final PreparedStatement statement = c.prepareStatement("INSERT INTO Book (ISBN, book_name, author_id, genre_id, description, isPurchaseable) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, newBook.getISBN());
            statement.setString(2, newBook.getName());
            statement.setObject(3, newBook.getAuthorID());
            statement.setObject(4, newBook.getGenre().ordinal());
            statement.setString(5, newBook.getDescription());
            statement.setBoolean(6, true);

            statement.executeUpdate();
            statement.close();

            return newBook;
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new DuplicateISBNException("For ISBN " + newBook.getISBN());
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
            final PreparedStatement statement = c.prepareStatement ("SELECT b.*, u.first_name, u.last_name FROM Book b "
                    + "join author a on a.author_id=b.author_id "
                    + "join user u on u.username=a.username "
                    + "where b.genre_id= ?");
            statement.setInt(1, genre.ordinal());

            final ResultSet result = statement.executeQuery();
            while (result.next()){
                final Book book = fromResultSet(result);
                booksByGenre.add(book);
            }

            statement.close();
            result.close();
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new InvalidGenreException("Could not find " + genre + " in system");
        }
        return booksByGenre;
    }

    private Book fromResultSet(final ResultSet result) throws SQLException{
        final String bookName = result.getString("book_name");
        final String bookAuthor = result.getString("first_name") + " " + result.getString("last_name");
        final int bookAuthorID = result.getInt("author_id");
        final Genre bookGenre = Genre.values()[result.getInt("genre_id")];
        final String ISBN = result.getString("ISBN");
        final String description = result.getString("description");

        return new Book(bookName,bookAuthor,bookAuthorID,bookGenre,ISBN, description);

    }
}
