package honda.bookworm.Data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
import honda.bookworm.Business.Exceptions.InvalidGenreException;
import honda.bookworm.Business.Exceptions.Users.AuthorNotFoundException;
import honda.bookworm.Business.Exceptions.Users.DuplicateUserException;
import honda.bookworm.Business.Exceptions.Users.UserNotFoundException;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.Author;
import honda.bookworm.Object.Genre;
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
    public void removeUser(String username) {
        try (final Connection c = connection()) {
            String sql = "DELETE from USER where username = ?";

            final PreparedStatement statement = c.prepareStatement (sql);
            statement.setString(1, username);

            statement.executeUpdate();

            statement.close();

        } catch (final SQLException e) {
            e.printStackTrace();
            throw new UserNotFoundException("No Users found");
        }
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
    public User addUser(User currentUser) throws GeneralPersistenceException{
        try (final Connection c = connection()) {
            String sql = "INSERT INTO user VALUES(?, ?, ?, ?)";

            final PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, currentUser.getUsername());
            statement.setString(2, currentUser.getFirstName());
            statement.setString(3, currentUser.getLastName());
            statement.setString(4, currentUser.getPassword());

            statement.executeUpdate();
            statement.close();

            if(currentUser.canAuthorBooks())
            {
                addAuthor(currentUser);
            }

            return currentUser;
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new GeneralPersistenceException("Persistence operation encountered an unexpected error.");
        }
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

    private void addAuthor(User user) throws GeneralPersistenceException {
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

    @Override
    public boolean isGenreFavoriteOfUser(User user, Genre genre) throws UserNotFoundException {
        String sql = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END AS rowExists " +
                "FROM favoritegenre " +
                "WHERE user_username = ? AND genre_id = ?";
        boolean result = false;
        try (Connection c = connection()) {
            PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setInt(2, genre.ordinal());

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
    public boolean toggleUserGenreFavorite(User user, Genre genre) throws InvalidGenreException, GeneralPersistenceException {
        final String sql = "MERGE INTO favoritegenre fg " +
                "USING (VALUES (?,?)) AS data(username, genreID) " +
                "ON (fg.user_username = data.username AND fg.genre_id = data.genreID) " +
                "WHEN MATCHED THEN DELETE " +
                "WHEN NOT MATCHED THEN INSERT (user_username, genre_id) VALUES (data.username, data.genreID);";
        boolean result = false;

        try(Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setInt(2, genre.ordinal());

            statement.executeUpdate();
            result = isGenreFavoriteOfUser(user, genre);

            c.commit();
            statement.close();

        } catch (final SQLException | NullPointerException e) {

            if (e instanceof SQLIntegrityConstraintViolationException) {
                throw new InvalidGenreException("Could not find " + genre + " in system");
            } else if (e instanceof NullPointerException) {
                if(genre == null) {
                    throw new InvalidGenreException("Invalid genre value");
                }
                else {
                    throw new UserNotFoundException("User does not exist");
                }

            } else {
                e.printStackTrace();
            }
        }

        return result;
    }

    public List<Genre> getFavoriteGenreList(User user) throws InvalidGenreException {
        List<Genre> genreList = new ArrayList<>();
        try(Connection c = connection()){
            String sqlQuery = "SELECT g.* FROM favoritegenre fg "
                    + "join Genre g on g.genre_id = fg.genre_id "
                    + "where fg.user_username = ? " ;
            PreparedStatement statement = c.prepareStatement(sqlQuery);
            statement.setString(1,user.getUsername());
            ResultSet result = statement.executeQuery();

            while(result.next()){
                String genreName = result.getString("name").replace("-","");
                final Genre genre= Genre.valueOf(genreName);
                genreList.add(genre);
            }
        }catch (SQLException e){
            e.printStackTrace();
            throw new GeneralPersistenceException("Persistence operation encountered an unexpected error.");
        }

        return genreList;
    }

    public List<User> searchUsersByQuery(String query){
        List<User> results = new ArrayList<>();
        String sql = "SELECT u.*, a.author_id from User u "+
                "LEFT JOIN author a ON u.username= a.username "+
                "WHERE LOWER(u.username) LIKE LOWER(?) "+
                "OR LOWER(u.first_name) LIKE LOWER(?) "+
                "OR LOWER(u.last_name) LIKE LOWER(?) " +
                "OR (LOWER(u.first_name) || ' ' || LOWER(u.last_name)) LIKE LOWER(?)";
        String searchValue = "%" + query + "%";

        try (final Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement(sql);

            statement.setString(1, searchValue);
            statement.setString(2, searchValue);
            statement.setString(3, searchValue);
            statement.setString(4, searchValue);


            final ResultSet result = statement.executeQuery();

            while (result.next()) {
                final User user = fromResultSet(result);
                results.add(user);
            }

            statement.close();
            result.close();
        } catch (final SQLException e) {
            e.printStackTrace();
            throw new GeneralPersistenceException("User: " + query + " does not exist");
        }

        return results;
    }

    public String getUsernameFromAuthorID(int authorID) throws AuthorNotFoundException, GeneralPersistenceException{
        String username = "";

        try(Connection c = connection()){
            String sqlQuery = "SELECT * from Author where author_id = ?";
            PreparedStatement statement = c.prepareStatement(sqlQuery);
            statement.setInt(1,authorID);
            ResultSet result = statement.executeQuery();

            if(result.next()){
                username = result.getString("username");
            }else{
                throw new AuthorNotFoundException("No Author found by AuthorID: "+authorID);
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new GeneralPersistenceException(e.getMessage());
        }
        return username;
    }
}
