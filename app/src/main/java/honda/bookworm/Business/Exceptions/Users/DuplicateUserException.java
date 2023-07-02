package honda.bookworm.Business.Exceptions.Users;

public class DuplicateUserException extends UserException {
    public DuplicateUserException (String error) {
        super ("Duplication user found:\n" + error);
    }
}
