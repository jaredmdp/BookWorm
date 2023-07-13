package honda.bookworm.Business.Exceptions.Books;

public class DuplicateISBNException extends BookException {
    public DuplicateISBNException (String error) {
        super ("Duplicate book ISBN:\n" + error);
    }
}
