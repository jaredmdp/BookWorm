package honda.bookworm.Data;

import java.util.List;

import honda.bookworm.Object.Comment;

public interface ICommentPersistence {
    Comment addComment(Comment newComment);

    List<Comment> getCommentsByISBN(String ISBN);

    void removeAllCommentsOfUser(String username); //used for acceptence test only currently
}
