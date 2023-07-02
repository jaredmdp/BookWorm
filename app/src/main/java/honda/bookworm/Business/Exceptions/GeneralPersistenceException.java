package honda.bookworm.Business.Exceptions;

public class GeneralPersistenceException  extends RuntimeException {
    public GeneralPersistenceException (String error) {
        super ("General persistance error: " + error);
    }
}
