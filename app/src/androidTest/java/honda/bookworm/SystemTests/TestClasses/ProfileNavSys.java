package honda.bookworm.SystemTests.TestClasses;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.Is.is;

import android.view.KeyEvent;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.honda.bookworm.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import honda.bookworm.View.Home_ViewHandler;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProfileNavSys {
    @Rule
    public ActivityScenarioRule<Home_ViewHandler> activityRule = new ActivityScenarioRule<>(Home_ViewHandler.class);

    @Test
    public void openUserProfileSearch(){
        //click search logo button
        onView(withId(R.id.home_proceed_to_search)).perform(click());

        //click the user button
        onView(withId(R.id.search_for_users)).perform(click());

        //search by user
        onView(withId(R.id.search_search_input)).perform(typeText("notAuthor"));
        onView(withId(R.id.search_search_input)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

        //click the profile
        onView(withText("@notAuthor")).perform(scrollTo(),click());

        //check if the clicked profile matched
        onView(withText("@notAuthor")).check(matches(isDisplayed()));

        //since this is a user, check if written book is not displayed
        onView(withText("Written Books")).check(doesNotExist());
    }

    @Test
    public void openAuthorProfileSearch(){
        //click search logo button
        onView(withId(R.id.home_proceed_to_search)).perform(click());

        //click the user button
        onView(withId(R.id.search_for_users)).perform(click());

        //search by user
        onView(withId(R.id.search_search_input)).perform(typeText("rowling"));
        onView(withId(R.id.search_search_input)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

        //click the profile
        onView(withText("@rowling")).perform(scrollTo(),click());

        //check if the clicked profile matched
        onView(withText("@rowling")).check(matches(isDisplayed()));

        //since this is an author, check if written book is displayed
        onView(withText("Written Books")).check(matches(isDisplayed()));
    }

    @Test
    public void openProfileComments(){
        //go to books that have comments
        onView(withTagValue(is("The Hobbit"))).perform(scrollTo(), click());

        //click the discussions tab
        onView(withId(R.id.book_view_discussion_tab)).perform(click());

        //click a comment
        onView(withText("@martin")).perform(scrollTo(),click());

        //check if the clicked profile matched
        onView(withText("@martin")).check(matches(isDisplayed()));

        //since this is an author, check if written book is displayed
        onView(withText("Written Books")).check(matches(isDisplayed()));
    }
}
