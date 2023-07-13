package honda.bookworm.Business.Exceptions.Users;

public class UserException extends RuntimeException {
    public UserException (String error) {
        super (error);
    }
}
