package honda.bookworm.Business.Exceptions.Users;

public class AuthorNotFoundException extends UserException{
    public AuthorNotFoundException (String error) {
        super ("Author not found:\n" + error);
    }
}
