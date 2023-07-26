package honda.bookworm.SystemTests.TestClasses;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static honda.bookworm.SystemTests.SystemTestUtils.SystemTestUtils.firstView;

import androidx.test.espresso.contrib.RecyclerViewActions;
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
public class RecommendBooksSys {
    @Rule
    public ActivityScenarioRule<Home_ViewHandler> activityRule = new ActivityScenarioRule<>(Home_ViewHandler.class);

    @Test
    public void recommendBooks() throws InterruptedException {
        //check if user can see genres section of the home page
        onView(withText("Recommended Books")).check(matches(isDisplayed()));

        //check if any book exist on the home page
        onView(firstView(withId(R.id.books_horizontal_recyclerview))).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Thread.sleep(2000);

        pressBack();

        //scroll through the horizontal view
        onView(firstView(withId(R.id.books_horizontal_recyclerview))).
                perform(swipeLeft());
    }
}
