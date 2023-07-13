package honda.bookworm.Data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import honda.bookworm.Business.Exceptions.Books.DuplicateISBNException;
import honda.bookworm.Business.Exceptions.Books.InvalidISBNException;
import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
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
    public Book getBookByISBN(String ISBN) throws InvalidISBNException, GeneralPersistenceException {
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

    public List<Book> searchBooksByISBN(String ISBN) throws GeneralPersistenceException {

        String sql = "SELECT b.*, u.first_name, u.last_name " +
                "FROM Book b " +
                "JOIN Author a ON b.author_id = a.author_id " +
                "JOIN User u ON a.username = u.username " +
                "WHERE b.ISBN LIKE ?";

        String searchValue = "%" + ISBN + "%";
        List<Book> bookList = new ArrayList<>();

        try (final Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement(sql);

            statement.setString(1, searchValue);

            final ResultSet result = statement.executeQuery();

            while (result.next()) {
                final Book book = fromResultSet(result);
                bookList.add(book);
            }

            statement.close();
            result.close();
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new GeneralPersistenceException("Invalid ISBN");
        }

        return bookList;
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        String sql = "SELECT b.*, u.first_name, u.last_name " +
                "FROM Book b " +
                "JOIN Author a ON b.author_id = a.author_id " +
                "JOIN User u ON a.username = u.username " +
                "WHERE LOWER(b.book_name) LIKE LOWER(?)";

        String searchValue = "%" + title + "%";
        List<Book> bookList = new ArrayList<>();

        try (final Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement(sql);

            statement.setString(1, searchValue);

            final ResultSet result = statement.executeQuery();

            while (result.next()) {
                final Book book = fromResultSet(result);
                bookList.add(book);
            }

            statement.close();
            result.close();
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new GeneralPersistenceException("Invalid Title");
        }

        return bookList;
    }

    @Override
    public Book addBook(Book newBook) throws DuplicateISBNException {
        try (final Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement("INSERT INTO Book (ISBN, book_name, author_id, genre_id, description, isPurchaseable, image) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, newBook.getISBN());
            statement.setString(2, newBook.getName());
            statement.setObject(3, newBook.getAuthorID());
            statement.setObject(4, newBook.getGenre().ordinal());
            statement.setString(5, newBook.getDescription());
            statement.setBoolean(6, newBook.getPurchaseable());
            statement.setString(7, newBook.getCover());

            statement.executeUpdate();
            statement.close();

            return newBook;
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new DuplicateISBNException("For ISBN " + newBook.getISBN());
        }
    }

    @Override
    public List<Book> getBooksByAuthorID(int authorID) throws GeneralPersistenceException {
        String sql = "SELECT b.*, u.first_name, u.last_name FROM Book b " +
                "JOIN Author a ON b.author_id = a.author_id " +
                "JOIN User u ON a.username = u.username " +
                "WHERE a.author_id = ?";
        List<Book> booksByAuthor = new ArrayList<>();

        try (final Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement(sql);

            statement.setInt(1, authorID);

            final ResultSet result = statement.executeQuery();

            while (result.next()) {
                final Book book = fromResultSet(result);
                booksByAuthor.add(book);
            }

            statement.close();
            result.close();
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new GeneralPersistenceException("Author ID: " + authorID + " does not exist");
        }

        return booksByAuthor;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) throws GeneralPersistenceException {
        String sql = "SELECT b.*, u.first_name, u.last_name FROM Book b " +
                "JOIN Author a ON b.author_id = a.author_id " +
                "JOIN User u ON a.username = u.username " +
                "WHERE LOWER(u.first_name) LIKE LOWER(?) " +
                "OR LOWER(u.last_name) LIKE LOWER(?) " +
                "OR (LOWER(u.first_name) || ' ' || LOWER(u.last_name)) LIKE LOWER(?)";
        List<Book> booksByAuthor = new ArrayList<>();
        String searchValue = "%" + author + "%";

        try (final Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement(sql);

            statement.setString(1, searchValue);
            statement.setString(2, searchValue);
            statement.setString(3, searchValue);

            final ResultSet result = statement.executeQuery();

            while (result.next()) {
                final Book book = fromResultSet(result);
                booksByAuthor.add(book);
            }

            statement.close();
            result.close();
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new GeneralPersistenceException("Author: " + author + " does not exist");
        }

        return booksByAuthor;
    }
    @Override
    public List<Book> getBooksByGenre(Genre genre) throws GeneralPersistenceException {
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
            throw new GeneralPersistenceException("Could not find " + genre + " in system");
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
                if(Objects.requireNonNull(e.getMessage()).contains("101124")){
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
        final String coverEncoded = result.getString("image");
        final boolean isPurchaseable = result.getBoolean("isPurchaseable");

        return new Book(bookName,bookAuthor,bookAuthorID,bookGenre,ISBN, description, coverEncoded, isPurchaseable);
    }
}
