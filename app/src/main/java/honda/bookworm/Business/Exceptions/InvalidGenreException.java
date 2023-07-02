package honda.bookworm.Business.Exceptions;

public class InvalidGenreException extends RuntimeException {
    public InvalidGenreException(String error) {
        super("Invalid Genre:\n" + error);
    }
}
