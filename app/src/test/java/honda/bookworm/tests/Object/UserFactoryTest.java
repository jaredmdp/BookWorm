package honda.bookworm.tests.Object;

import org.junit.Test;

import honda.bookworm.Object.Author;
import honda.bookworm.Object.Factories.UserFactory;
import honda.bookworm.Object.User;

public class UserFactoryTest {
    @Test
    public void testMake(){
        System.out.println("Starting testMake");

        User testUser = new User("Miguell", "Ohara", "EdgeLord", "2099");
        User testAuthor = new Author("Peter B", "Parker", "DiscountSpiderman", "SecondBest");
        UserFactory userFactory = new UserFactory();

        User returned = userFactory.make(testUser.getFirstName(), testUser.getLastName(), testUser.getUsername(), testUser.getPassword(), "uSeR");
        assert(testUser.equals(returned));

        returned = userFactory.make(testAuthor.getFirstName(), testAuthor.getLastName(), testAuthor.getUsername(), testAuthor.getPassword(), "auTHor");
        assert(testAuthor.equals(returned));

        System.out.println("Finished testMake");
    }
}
