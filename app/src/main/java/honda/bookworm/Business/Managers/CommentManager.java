package honda.bookworm.Business.Managers;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.InvalidCommentException;
import honda.bookworm.Business.Exceptions.Users.UserException;
import honda.bookworm.Business.ICommentManager;
import honda.bookworm.Data.ICommentPersistence;
import honda.bookworm.Object.Comment;
import honda.bookworm.Object.User;

public class CommentManager implements ICommentManager {

    private ICommentPersistence commentPersistence;

    private static final int MIN_COMMENT = 1;
    private static final int MAX_COMMENT = 280;

    public CommentManager() {
        commentPersistence = Services.getCommentPersistence();
    }

    public CommentManager(ICommentPersistence commentPersistence){
        this.commentPersistence = commentPersistence;
    }

    public Comment leaveComment(String ISBN, String comment) throws UserException, InvalidCommentException {
        User currentUser = Services.getActiveUser();

        if(currentUser == null){
            throw new UserException("Please log in to leave a comment");
        }

        validateComment(comment);

        Comment newComment = new Comment(currentUser.getUsername(), ISBN, comment);

        return commentPersistence.addComment(newComment);
    }

    //Validator functions----------------------------------------------------------------------------
    private void validateComment(String comment) throws InvalidCommentException {
        validateCommentLength(comment);
    }

    private void validateCommentLength(String comment) throws InvalidCommentException{
        if(StringValidator.isTooLong(comment, MAX_COMMENT)){
            throw new InvalidCommentException("Comment cannot exceed " +MAX_COMMENT+" characters");
        }

        if(StringValidator.isTooShort(comment, MIN_COMMENT)){
            throw new InvalidCommentException("Comment must be at least " +MIN_COMMENT+" characters long");
        }
    }
}
