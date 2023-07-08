package honda.bookworm.Business.Exceptions;

public class InvalidSearchException extends RuntimeException {
    public InvalidSearchException(String error) {
        super("Invalid Search Input:\n" + error);
    }
}