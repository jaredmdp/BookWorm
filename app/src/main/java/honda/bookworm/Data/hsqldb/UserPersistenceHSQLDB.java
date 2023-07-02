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
import honda.bookworm.Business.Exceptions.Users.*;
import honda.bookworm.Business.Exceptions.GeneralPersistenceException;

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
            String sql = "SELECT user.*, author.author_id FROM user LEFT JOIN author "
                    + "ON user.username=author.username";

            final PreparedStatement statement = c.prepareStatement (sql);
            final ResultSet result = statement.executeQuery();

            while (result.next()){
                final User user = fromResultSet(result);
                allUsers.add(user);
            }

            statement.close();
            result.close();
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new UserNotFoundException("No Users found");
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
            String sql = "SELECT user.*, author.author_id FROM user LEFT JOIN author "
                    + "ON user.username=author.username "
                    + "WHERE user.username=?";

            final PreparedStatement statement = c.prepareStatement (sql);
            statement.setString(1, currentUsername);

            final ResultSet result = statement.executeQuery();

            if(result.next()){
                user = fromResultSet(result);
            }

            statement.close();
            result.close();
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new GeneralPersistenceException("For Username: " + currentUsername);
        }

        if (user == null) {
            throw new UserNotFoundException("For user: " + currentUsername);
        }

        return user;
    }

    @Override
    public User addUser(User currentUser) {
        try (final Connection c = connection()) {
            String sql = "INSERT INTO user VALUES(?, ?, ?, ?)";

            final PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, currentUser.getUsername());
            statement.setString(2, currentUser.getFirstName());
            statement.setString(3, currentUser.getLastName());
            statement.setString(4, currentUser.getPassword());

            statement.executeUpdate();
            statement.close();

            if(currentUser instanceof Author)
            {
                addAuthor(currentUser);
            }

            return currentUser;
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new DuplicateUserException("For User " + currentUser);
        }
    }

    @Override
    public User removeUser(User currentUser) {
        return null;
    }

    private User fromResultSet(final ResultSet result) throws SQLException {
        User parsed;

        final String firstName = result.getString("first_name");
        final String lastName = result.getString("last_name");
        final String username = result.getString("username");
        final String password = result.getString("password");
        final String authorID = result.getString("author_id");

        if(authorID != null) {
            parsed = new Author(firstName, lastName, username, password, Integer.parseInt(authorID));
        }
        else {
            parsed = new User(firstName, lastName, username, password);
        }

        return parsed;

    }

    private void addAuthor(User user) {
        try (final Connection c = connection()) {
            String sqlIn = "INSERT INTO author (username) VALUES(?)";
            String sqlGet = "SELECT author_id FROM author where username=?";

            final PreparedStatement insert = c.prepareStatement(sqlIn);
            insert.setString(1, user.getUsername());

            insert.executeUpdate();
            insert.close();

            final PreparedStatement get = c.prepareStatement(sqlGet);
            get.setString(1, user.getUsername());

            final ResultSet result = get.executeQuery();
            if(result.next()) {
                ((Author)user).setAuthorID(result.getInt("author_id"));
            }

        } catch (final SQLException e) {
            e.printStackTrace();
            throw new GeneralPersistenceException("Persistence operation encountered an unexpected error.");
        }
    }
}
