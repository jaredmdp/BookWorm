package honda.bookworm.Business.Exceptions;

public class ImageNotFoundException  extends RuntimeException {
    public ImageNotFoundException (String error) {
        super ("Image not found:\n" + error);
    }
}
