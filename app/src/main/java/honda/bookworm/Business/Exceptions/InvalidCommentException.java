package honda.bookworm.Business.Exceptions;

public class InvalidCommentException extends RuntimeException{
    public InvalidCommentException(String message){super("Invalid Comment: " + message);}
}
