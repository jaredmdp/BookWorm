package honda.bookworm.SystemTests.TestClasses;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagValue;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;

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
public class BookImageSys {
    @Rule
    public ActivityScenarioRule<Home_ViewHandler> activityRule = new ActivityScenarioRule<>(Home_ViewHandler.class);

    @Test
    public void viewImage(){
        //go to a specific book details page with no image
        onView(withText("Neuromancer")).perform(scrollTo(),click());

        //check if image is not there
        onView(allOf(withId(R.id.book_view_book_cover), withTagValue(is(false)))).check(matches(isDisplayed()));

        pressBack();

        //go to a specific book details page with image
        onView(withTagValue(is("Dune"))).perform(scrollTo(), click());

        //2nd, check if image is there
        onView(allOf(withId(R.id.book_view_book_cover), withTagValue(is(true)))).check(matches(isDisplayed()));

    }
}
