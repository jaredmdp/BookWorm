package honda.bookworm.Business.Managers;

public class StringValidator {

    //String checker helper functions
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean containsWhitespace(String str) {
        return str.contains(" ");
    }

    //alphabet only
    public static boolean isAlphaOnly(String str) {
        return str.matches("[a-zA-Z]+");
    }
    public static boolean isAlphaDashes(String str) {
        return str.matches("[a-zA-Z- ]+");
    }

    public static boolean isNumericOnly(String str) {
        return str.matches("[0-9]+");
    }

    //alphabets + space + period + numbers
    public static boolean isValidName(String str) {
        return str.matches("[a-zA-Z0-9 .]+");
    }

    //alphabets + numbers + dash and underscores
    public static boolean isValidInput(String input) {
        return input.matches("[a-zA-Z0-9_-]+");
    }

    public static boolean isTooLong(String str, int maxLength) {
        return str.length() > maxLength;
    }

    public static boolean isTooShort(String str, int minLength) {
        return str.length() < minLength;
    }

    public static boolean isExactly(String str, int length){
        return str.length() == length;
    }
}

