package honda.bookworm.Business.Exceptions;

public class InputTooLongException extends RuntimeException{
    public InputTooLongException (String  error) {
        super("Input is too long:\n" + error);
    }
}
