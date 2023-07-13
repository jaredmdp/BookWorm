package honda.bookworm.Business.Exceptions.Users;

public class InvalidSignupException extends UserException {
    public InvalidSignupException (String error) {
        super ("Invalid Signup:\n" + error);
    }
}