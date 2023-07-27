package honda.bookworm.SystemTests.TestClasses;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.honda.bookworm.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.GeneralPersistenceException;
import honda.bookworm.Data.ICommentPersistence;
import honda.bookworm.Object.User;
import honda.bookworm.View.Home_ViewHandler;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CommentSys {
    @Rule
    public ActivityScenarioRule<Home_ViewHandler> activityRule = new ActivityScenarioRule<>(Home_ViewHandler.class);

    @Test
    public void viewComment() throws InterruptedException {
        //open hobbit book
        onView(withTagValue(is("The Hobbit"))).perform(scrollTo(),click());

        //confirm right book is opened
        onView(withId(R.id.book_view_book_title)).check(matches(withText("Title:  The Hobbit")));

        onView(withId(R.id.book_view_discussion_tab)).perform(scrollTo(),click());
        Thread.sleep(2000); //give some time incase loading comments take time

        onView(withId(R.id.book_view_scrollview)).perform(swipeUp()); //scroll to very bottom of the comments

        //verify if these three comments exist
        onView(allOf(withId(R.id.recycler_comment_username),withText("@martin"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.recycler_comment_username),withText("@rowling"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.recycler_comment_username),withText("@notAuthor"))).check(matches(isDisplayed()));
    }


    @Test
    public void addCommentNoUser() throws InterruptedException {
        String msg = "Test c0mment should not 4dd. GibberISH hopefu1ly doesnt Already 3x!st";
        //open hobbit book
        onView(withTagValue(is("The Hobbit"))).perform(scrollTo(),click());

        //confirm right book is opened
        onView(withId(R.id.book_view_book_title)).check(matches(withText("Title:  The Hobbit")));

        onView(withId(R.id.book_view_discussion_tab)).perform(scrollTo(),click());

        onView(withId(R.id.book_view_comment_input)).check(matches(isDisplayed()));
        onView(withId(R.id.book_view_comment_input)).perform(click(),typeTextIntoFocusedView(msg));
        onView(withId(R.id.book_view_comment_submit)).perform(click());
        Thread.sleep(1500); //slow down so user can see the toast appear
        onView(withId(R.id.book_view_comment_input)).check(matches(withText("")));
        onView(allOf(withId(R.id.recycler_comment_content),withText(msg))).check(doesNotExist());
    }

    @Test
    public void addComment() throws InterruptedException {
        User user = Services.getUserPersistence().getUserByUsername("testAuthor");
        removeCommentsByUser(user.getUsername());
        String testMsg = "Test comment should be added";
        String bookName = "Dune";

        //click user profile button to login
        onView(withId(R.id.home_userProfile_button)).perform(click());

        //login to existing author account
        onView(withId(R.id.userlogin_username_input)).perform(typeText(user.getUsername()));
        onView(withId(R.id.userlogin_password_input)).perform(typeText(user.getPassword()));
        closeSoftKeyboard();

        onView(withId(R.id.userlogin_login_button)).perform(click());

        onView(withTagValue(is(bookName))).perform(scrollTo(), click());
        onView(withId(R.id.book_view_book_title)).check(matches(withText("Title:  "+bookName)));

        onView(withId(R.id.book_view_discussion_tab)).perform(scrollTo(), click());
        onView(withId(R.id.book_view_comment_input)).perform(scrollTo());

        onView(withId(R.id.book_view_comment_input)).check(matches(isDisplayed()));
        onView(withId(R.id.book_view_comment_input)).perform(scrollTo(),click(), typeTextIntoFocusedView(testMsg));
        onView(withId(R.id.book_view_comment_submit)).perform(click());
        Thread.sleep(4000); //Sometimes physical device is too fast. So we set a delay
        onView(withId(R.id.book_view_comment_input)).check(matches(withText("")));
        onView(allOf(withId(R.id.recycler_comment_content),withText(testMsg))).perform(scrollTo());
        onView(allOf(withId(R.id.recycler_comment_username),withText("@"+user.getUsername()))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.recycler_comment_content),withText(testMsg))).check(matches(isDisplayed()));

        removeCommentsByUser(user.getUsername());
    }

    private  void removeCommentsByUser(String username){
        ICommentPersistence cp = Services.getCommentPersistence();
        cp.removeAllCommentsOfUser(username);
    }


}
