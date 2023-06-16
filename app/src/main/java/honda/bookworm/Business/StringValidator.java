package honda.bookworm.Business;

public class StringValidator {

    //String checker helper functions
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean containsWhitespace(String str) {
        return str.contains(" ");
    }

    public static boolean isAlphaOnly(String str) {
        return str.matches("[a-zA-Z]+");
    }

    public static boolean isValidInput(String input) {
        return input.matches("[a-zA-Z0-9_-]+");
    }

    public static boolean isTooLong(String str) {
        int maxLength = 16;
        return str.length() > maxLength;
    }

    public static boolean isTooShort(String str) {
        int minLength = 3;
        return str.length() < minLength;
    }
}

