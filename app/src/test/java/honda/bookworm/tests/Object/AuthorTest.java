package honda.bookworm.tests.Object;

import org.junit.Test;
import static org.junit.Assert.*;

import honda.bookworm.Object.Author;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.Genre;

public class AuthorTest {

    @Test
    public void testNewAuthor() {

        Author author = new Author("John", "Doe", "johndoe", "password", 0);
        assertNotNull(author);
        assertEquals("John", author.getFirstName());
        assertEquals("Doe", author.getLastName());
        assertEquals("johndoe", author.getUsername());
        assertEquals("password", author.getPassword());
        assertTrue(author.getWrittenBooks().isEmpty());
    }

    @Test
    public void testAddWrittenBook() {
        System.out.println("\nStarting AddWrittenBook");

        Author author = new Author("Jane", "Smith", "janesmith", "password", 0);

        Book book1 = new Book("Book 1", "Author 1", 0, Genre.Fiction, "1234");

        assertTrue(author.getWrittenBooks().isEmpty());
        author.addWrittenBook(book1);

        assertEquals(1, author.getWrittenBooks().size());
        assertTrue(author.getWrittenBooks().contains(book1));

        System.out.println("\nFinished AddWrittenBook");
    }

    @Test
    public void testRemoveWrittenBook() {
        System.out.println("\nStarting RemoveWrittenBook");

        Author author = new Author("Jane", "Smith", "janesmith", "password", 0);

        Book book1 = new Book("Book 1", "Author 1", 0, Genre.Fiction, "1234");
        Book book2 = new Book("Book 2", "Author 2", 0, Genre.Fantasy, "1235");

        author.addWrittenBook(book1);
        author.addWrittenBook(book2);
        author.removeWrittenBook(book1);

        assertEquals(1, author.getWrittenBooks().size());
        assertFalse(author.getWrittenBooks().contains(book1));
        assertTrue(author.getWrittenBooks().contains(book2));

        System.out.println("\nFinished RemoveWrittenBook");
    }

    @Test
    public void testAuthorEquals() {
        System.out.println("\nStarting AuthorEquals");

        Author author1 = new Author("John", "Wick", "Johnwick", "hunter2", 0);
        Author author2 = new Author("John", "Wick", "Johnwick", "hunter2", 0);
        assertEquals(author1, author2);
        assertEquals(author1,author1);
        System.out.println("\nFinished AuthorEquals");
    }

    @Test
    public void testUAuthorNotEquals() {
        System.out.println("\nStarting AuthorNotEquals");

        Author user1 = new Author("John", "Doe", "johndoe", "hunter2", 0);
        Author user2 = new Author("Jane", "Smith", "janesmith", "hunter3", 1);
        assertNotEquals(user1, user2);

        System.out.println("\nFinished AuthorEquals");
    }

    @Test
    public void testAuthorToString() {
        System.out.println("\nStarting AuthorToString");

        Author author = new Author("John", "Doe", "johndoe", "hunter3", 0);

        String expectedString = "Author: firstName:'John', lastName:'Doe', username:'johndoe', password:'hunter3'";
        assertEquals(expectedString, author.toString());

        System.out.println("\nFinished AuthorToString");
    }
}


