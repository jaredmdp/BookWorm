package honda.bookworm.Business.Exceptions.Users;

public class InvalidPasswordException extends UserException {
    public InvalidPasswordException (String error) {
        super ("Invalid password:\n" + error);
    }
}
