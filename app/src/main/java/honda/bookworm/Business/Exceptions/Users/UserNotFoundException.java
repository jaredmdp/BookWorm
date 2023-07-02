package honda.bookworm.Business.Exceptions.Users;

public class UserNotFoundException extends UserException {
    public UserNotFoundException (String error) {
        super ("User not found:\n" + error);
    }
}
