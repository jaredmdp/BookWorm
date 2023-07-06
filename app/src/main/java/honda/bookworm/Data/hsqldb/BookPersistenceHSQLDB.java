package honda.bookworm.Data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Business.Exceptions.Books.DuplicateISBNException;
import honda.bookworm.Business.Exceptions.Books.InvalidISBNException;
import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
import honda.bookworm.Business.Exceptions.InvalidGenreException;
import honda.bookworm.Business.Exceptions.Users.UserNotFoundException;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;
import honda.bookworm.Object.User;

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
        Book result;

        try (final Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement("Select b.*, u.first_name, u.last_name from PUBLIC.Book b "+
                    "join author a on a.author_id = b.author_id " +
                    "join user u on u.username=a.username "+
                    "where b.ISBN = ?");

            statement.setString(1, ISBN);
            ResultSet resultSet = statement.executeQuery();

            // Process the result
            if (resultSet.next()) {
                result = this.fromResultSet(resultSet);
            }else {
                throw new InvalidISBNException(ISBN);
            }

        } catch (final SQLException e) {
            e.printStackTrace();
            throw new GeneralPersistenceException(e.getMessage());
        }
        return result;
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

    @Override
    public boolean isBookFavoriteOfUser(User user, String isbn) throws UserNotFoundException {
        String sql = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END AS rowExists " +
                "FROM favoritebook " +
                "WHERE user_username = ? AND ISBN = ?";
        boolean result = false;
        try (Connection c = connection()) {
            PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, isbn);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the result
            if (resultSet.next()) {
                result = resultSet.getBoolean("rowExists");
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            if (e instanceof NullPointerException) {
                throw new UserNotFoundException("User does not exist");
            }
        }

        return result;
    }

    @Override
    public boolean toggleUserBookFavorite(User user, String isbn) throws InvalidISBNException, GeneralPersistenceException {
        final String sql = "MERGE INTO favoritebook fb " +
                "USING (VALUES (?,?)) AS data(username, bookISBN) " +
                "ON (fb.user_username = data.username AND fb.ISBN = data.bookISBN) " +
                "WHEN MATCHED THEN DELETE " +
                "WHEN NOT MATCHED THEN INSERT (user_username, ISBN) VALUES (data.username, data.bookISBN);";
        boolean result = false;

        try(Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, isbn);

            statement.executeUpdate();
            result = isBookFavoriteOfUser(user, isbn);

            c.commit();
            statement.close();

        } catch (final SQLException | NullPointerException e) {

            if (e instanceof SQLIntegrityConstraintViolationException) {
                if(((SQLIntegrityConstraintViolationException) e).getMessage().contains("101124")){
                    throw new InvalidISBNException(isbn);
                }
            } else if (e instanceof NullPointerException) {
                throw new UserNotFoundException("User does not exist");
            } else {
                e.printStackTrace();
            }
        }

        return result;
    }

    public List<Book> getFavoriteBookList(User user) {
        List<Book> bookList = new ArrayList<>();
        try(Connection c = connection()){
            String sqlQuery = "SELECT b.*, u.first_name, u.last_name FROM favoritebook fb "
                    + "join Book b on b.isbn = fb.isbn "
                    + "join author a on a.author_id = b.author_id "
                    + "join user u on u.username = a.username "
                    + "where fb.user_username = ? " ;
            PreparedStatement statement = c.prepareStatement(sqlQuery);
            statement.setString(1,user.getUsername());
            ResultSet result = statement.executeQuery();

            while(result.next()){
                final Book book= fromResultSet(result);
                bookList.add(book);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return bookList;
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
