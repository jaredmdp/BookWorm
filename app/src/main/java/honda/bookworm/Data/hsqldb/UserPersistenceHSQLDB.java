package honda.bookworm.Data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.User;

public class UserPersistenceHSQLDB implements IUserPersistence {

    private final String dbPath;

    public UserPersistenceHSQLDB(final String dbPath){
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        try (final Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement ("SELECT * FROM user");
            final ResultSet result = statement.executeQuery();

            while (result.next()){
                final User user = fromResultSet(result);
                allUsers.add(user);
            }

            statement.close();
            result.close();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return allUsers;
    }

    @Override
    public List<Author> getAllAuthors() {
        return null;
    }

    @Override
    public List<Book> getAllWrittenBooks(String author) {
        return null;
    }

    @Override
    public User getUserByUsername(String currentUsername) {
        User user = null;
        try (final Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement ("SELECT * FROM User where username = ?");
            statement.setString(1, currentUsername);

            final ResultSet result = statement.executeQuery();

            if(result.next()){
                user = fromResultSet(result);
            }

            statement.close();
            result.close();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User addUser(User currentUser) {
        try (final Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement("INSERT INTO user VALUES(?, ?, ?, ?)");
            statement.setString(1, currentUser.getUsername());
            statement.setString(2, currentUser.getFirstName());
            statement.setString(3, currentUser.getLastName());
            statement.setString(4, currentUser.getPassword());

            statement.executeUpdate();

            return currentUser;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User removeUser(User currentUser) {
        return null;
    }

    private User fromResultSet(final ResultSet result) throws SQLException{
        final String firstName = result.getString("first_name");
        final String lastName = result.getString("last_name");
        final String username = result.getString("username");
        final String password = result.getString("password");

        return new User(firstName, lastName, username, password);

    }
}
