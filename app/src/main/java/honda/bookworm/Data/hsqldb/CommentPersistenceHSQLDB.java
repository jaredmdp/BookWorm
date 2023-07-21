package honda.bookworm.Data.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
import honda.bookworm.Data.ICommentPersistence;
import honda.bookworm.Object.Comment;

public class CommentPersistenceHSQLDB implements ICommentPersistence {
    private final String dbPath;

    public CommentPersistenceHSQLDB(final String dbPath){
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    public Comment addComment(Comment newComment) throws GeneralPersistenceException {
        try (final Connection c = connection()) {
            final PreparedStatement statement = c.prepareStatement("INSERT INTO COMMENT (user_username, ISBN, comment_text, time) VALUES (?, ?, ?, ?)");

            Timestamp commentTime = new Timestamp(System.currentTimeMillis());

            statement.setString(1, newComment.getUsername());
            statement.setString(2, newComment.getISBN());
            statement.setString(3, newComment.getComment());
            statement.setTimestamp(4, commentTime);

            statement.executeUpdate();
            statement.close();

            newComment.setTime(commentTime.toString());

            return newComment;
        } catch (final SQLException e) {
            throw new GeneralPersistenceException("Failed to add comment");
        }
    }
}
