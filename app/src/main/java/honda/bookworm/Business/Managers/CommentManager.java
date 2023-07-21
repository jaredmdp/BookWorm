package honda.bookworm.Business.Managers;

import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
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

    public Comment leaveComment(String ISBN, String comment) throws UserException, InvalidCommentException, GeneralPersistenceException {
        User currentUser = Services.getActiveUser();

        if(currentUser == null){
            throw new UserException("Please log in to leave a comment");
        }

        validateComment(comment);

        Comment newComment = new Comment(currentUser.getUsername(), ISBN, comment);

        try{
            return commentPersistence.addComment(newComment);
        } catch(GeneralPersistenceException e){
            throw new GeneralPersistenceException("Could not save comment, try again");
        }
    }

    public List<Comment> getCommentsOnBook(String ISBN) throws GeneralPersistenceException{
        try{
            return commentPersistence.getCommentsByISBN(ISBN);
        } catch(GeneralPersistenceException e){
            throw new GeneralPersistenceException("Couldn't get comments, try reloading the page");
        }
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
