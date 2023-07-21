package honda.bookworm.Business;

import java.util.List;

import honda.bookworm.Object.Comment;

public interface ICommentManager {
    Comment leaveComment(String ISBN, String comment);
    List<Comment> getCommentsOnBook(String ISBN);
}
