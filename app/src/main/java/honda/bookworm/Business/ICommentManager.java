package honda.bookworm.Business;

import honda.bookworm.Object.Comment;

public interface ICommentManager {
    Comment leaveComment(String ISBN, String comment);
}
