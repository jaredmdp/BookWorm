package honda.bookworm.tests.Business;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import honda.bookworm.Business.Managers.StringValidator;

public class StringValidatorTest {

    @Test
    public void testIsEmpty() {
        System.out.println("Starting testIsEmpty");

        assertTrue(StringValidator.isEmpty(null));
        assertTrue(StringValidator.isEmpty(""));
        assertTrue(StringValidator.isEmpty(" "));
        assertFalse(StringValidator.isEmpty("Hello"));

        System.out.println("Finished testIsEmpty");
    }

    @Test
    public void testContainsWhitespace() {
        System.out.println("Starting testContainsWhitespace");

        assertTrue(StringValidator.containsWhitespace("Hello World"));
        assertFalse(StringValidator.containsWhitespace("HelloWorld"));
        assertFalse(StringValidator.containsWhitespace(""));

        System.out.println("Finished testContainsWhitespace");
    }

    @Test
    public void testIsAlphaOnly() {
        System.out.println("Starting testIsAlphaOnly");

        assertTrue(StringValidator.isAlphaOnly("abc"));
        assertTrue(StringValidator.isAlphaOnly("ABC"));
        assertFalse(StringValidator.isAlphaOnly("abc123"));
        assertFalse(StringValidator.isAlphaOnly("abc!"));

        System.out.println("Finished testIsAlphaOnly");
    }

    @Test
    public void testIsValidInput() {
        System.out.println("Starting testIsValidInput");

        assertTrue(StringValidator.isValidInput("abc"));
        assertTrue(StringValidator.isValidInput("ABC"));
        assertTrue(StringValidator.isValidInput("abc123"));
        assertTrue(StringValidator.isValidInput("abc_-"));
        assertFalse(StringValidator.isValidInput("abc!"));
        assertFalse(StringValidator.isValidInput("ab/c@d/ef"));

        System.out.println("Finished testIsValidInput");
    }

    @Test
    public void testIsTooLong() {
        System.out.println("Starting testIsTooLong");

        assertTrue(StringValidator.isTooLong("ThisStringIsTooLong"));
        assertFalse(StringValidator.isTooLong("Short"));

        System.out.println("Finished testIsTooLong");
    }

    @Test
    public void testIsTooShort() {
        System.out.println("Starting testIsTooShort");

        assertTrue(StringValidator.isTooShort("A"));
        assertFalse(StringValidator.isTooShort("LongEnough"));

        System.out.println("Finished testIsTooShort");
    }
}
