package honda.bookworm.Business.Exceptions.Books;

public class InvalidBookException extends BookException {
    public InvalidBookException (String error) {
        super ("Invalid Book:\n" + error);
    }
}
