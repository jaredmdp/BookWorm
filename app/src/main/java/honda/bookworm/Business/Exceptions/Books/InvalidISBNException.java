package honda.bookworm.Business.Exceptions.Books;

public class InvalidISBNException extends BookException{
    public InvalidISBNException (String error) {
        super ("Invalid ISBN:\n" + error);
    }
}
