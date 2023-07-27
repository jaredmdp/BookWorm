package honda.bookworm.SystemTests.TestClasses;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static honda.bookworm.SystemTests.SystemTestUtils.SystemTestUtils.firstView;
import static honda.bookworm.SystemTests.SystemTestUtils.SystemTestUtils.getText;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.honda.bookworm.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import honda.bookworm.Business.Managers.UserPreference;
import honda.bookworm.Object.Book;
import honda.bookworm.Object.User;
import honda.bookworm.View.Home_ViewHandler;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FavBookSys {
    @Rule
    public ActivityScenarioRule<Home_ViewHandler> activityRule = new ActivityScenarioRule<>(Home_ViewHandler.class);

    @Test
    public void favoriteBook(){
        //click the user profile button on home page
        onView(withId(R.id.home_userProfile_button)).perform(click());

        //login to an existing account
        onView(withId(R.id.userlogin_username_input)).perform(typeText("rowling"));
        onView(withId(R.id.userlogin_password_input)).perform(typeText("harrypotter"));
        closeSoftKeyboard();

        //click login button
        onView(withId(R.id.userlogin_login_button)).perform(click());

        clearAllFavoriteBooks();

        //now in homepage, click the first book
        onView(firstView(withId(R.id.books_horizontal_recyclerview))).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //get text for checking
        String bookISBN = getText(withId(R.id.book_view_book_isbn));

        //click the favorite button
        onView(withId(R.id.book_view_fav_book_toggle)).perform(click());

        //check if toggle button is checked
        onView(withId(R.id.book_view_fav_book_toggle)).check(matches(isChecked()));

        //go back to homepage
        pressBack();

        //go to userprofile
        onView(withId(R.id.home_userProfile_button)).perform(click());
        onView(withId(R.id.home_userProfile_viewProfile)).perform(click());

        //check if the favorite book is there
        onView(firstView(withId(R.id.books_horizontal_recyclerview))).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.book_view_book_isbn)).check(matches(withText(bookISBN)));

        //logout
        pressBack();
        pressBack();
        onView(withId(R.id.home_userProfile_button)).perform(click());
        onView(withId(R.id.home_userProfile_logoutButton)).perform(click());
    }

    private void clearAllFavoriteBooks(){
        User user = new User("J.K.", "Rowling", "rowling", "harrypotter");
        UserPreference userPreference = new UserPreference();
        List<Book> allBooks = userPreference.getFavoriteBookList(user);
        System.out.println(allBooks);
        for (int i=0; i<allBooks.size(); i++){
            userPreference.bookFavouriteToggle(allBooks.get(i).getISBN());
        }
    }
}
