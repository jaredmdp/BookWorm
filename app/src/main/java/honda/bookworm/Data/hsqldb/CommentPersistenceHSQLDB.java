package honda.bookworm.Data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
import honda.bookworm.Data.ICommentPersistence;
import honda.bookworm.Object.Comment;

public class CommentPersistenceHSQLDB implements ICommentPersistence {
    private final String dbPath;

    public CommentPersistenceHSQLDB(final String dbPath){
        this.dbPath = dbPath;
    }

    private synchronized Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    public synchronized Comment addComment(Comment newComment) throws GeneralPersistenceException {
        try (final Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement("INSERT INTO COMMENT (user_username, ISBN, comment_text, time) VALUES (?, ?, ?, ?)");

            Timestamp commentTime = new Timestamp(newComment.timeStamp);

            statement.setString(1, newComment.getUsername());
            statement.setString(2, newComment.getISBN());
            statement.setString(3, newComment.getComment());
            statement.setTimestamp(4, commentTime);

            statement.executeUpdate();
            statement.close();

            return newComment;
        } catch (final SQLException e) {
            throw new GeneralPersistenceException("Failed to add comment");
        }
    }

    public synchronized List<Comment> getCommentsByISBN(String ISBN) throws GeneralPersistenceException {
        try(final Connection c = connection()){
            final PreparedStatement statement = c.prepareStatement("SELECT * FROM COMMENT WHERE ISBN = ? ORDER BY time DESC");

            statement.setString(1, ISBN);

            List<Comment> comments = new ArrayList<Comment>();
            final ResultSet result = statement.executeQuery();

            while(result.next()){
                comments.add(fromResultSet(result));
            }

            statement.close();

            return comments;
        } catch(SQLException e){
            throw new GeneralPersistenceException("Failed to retrieve comments");
        }
    }

    private synchronized Comment fromResultSet(ResultSet result) throws SQLException {
        final String username = result.getString("user_username");
        final String ISBN = result.getString("ISBN");
        final String comment = result.getString("comment_text");
        final Timestamp time = result.getTimestamp("time");

        Comment newComment = new Comment(username, ISBN, comment);
        newComment.setTime(time.getTime());

        return newComment;
    }
}
