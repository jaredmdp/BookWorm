package honda.bookworm.SystemTests.TestClasses;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.core.AllOf.allOf;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.honda.bookworm.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import honda.bookworm.Application.Services;
import honda.bookworm.Data.IBookPersistence;
import honda.bookworm.Object.Book;
import honda.bookworm.View.Home_ViewHandler;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddBooksSys {
    @Rule
    public ActivityScenarioRule<Home_ViewHandler> activityRule = new ActivityScenarioRule<>(Home_ViewHandler.class);
    private IBookPersistence bookPersistence;

    @Before
    public void setup(){
        bookPersistence = Services.getBookPersistence();
        resetAllBooks();
    }

    @Test
    public void addBook(){
        //click user profile button to login
        onView(withId(R.id.home_userProfile_button)).perform(click());

        //login to existing author account
        onView(withId(R.id.userlogin_username_input)).perform(typeText("testAuthor"));
        onView(withId(R.id.userlogin_password_input)).perform(typeText("123456"));
        closeSoftKeyboard();

        onView(withId(R.id.userlogin_login_button)).perform(click());

        //navigate to user profile
        onView(withId(R.id.home_userProfile_button)).perform(click());
        onView(withId(R.id.home_userProfile_viewProfile)).perform(click());

        //click the edit toggle button
        onView(withId(R.id.userprofile_edit_toggle)).perform(click());

        //navigate to add book page
        onView(allOf(withId(R.id.books_horizontal_button), isDisplayed())).perform(click());

        //fill books information
        onView(withId(R.id.addbook_booktitle_input)).perform(typeText("testAuthorBook"));
        closeSoftKeyboard();

        onView(withId(R.id.addbook_ISBN_input)).perform(typeText("1234567890123"));
        closeSoftKeyboard();

        onView(withId(R.id.addbook_genre_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        closeSoftKeyboard();

        onView(withId(R.id.addbook_description_input)).perform(typeText("hello world"));
        closeSoftKeyboard();

        //click the add book button
        onView(withId(R.id.addbook_addtobook_button)).perform(scrollTo(),click());

        //check if the books exist from the author user profile
        onView(withText("testAuthorBook")).check(matches(isDisplayed()));

        //logout
        pressBack();
        onView(withId(R.id.home_userProfile_button)).perform(click());
        onView(withId(R.id.home_userProfile_logoutButton)).perform(click());
    }
    @After
    public void tearDown(){
        resetAllBooks();
    }

    private void resetAllBooks(){
        List<Book> allBooks = bookPersistence.getBooksByAuthorID(12);
        if(allBooks != null){
            for (Book book : allBooks){
                bookPersistence.removeBookByISBN(book.getISBN());
            }
        }
    }
}
