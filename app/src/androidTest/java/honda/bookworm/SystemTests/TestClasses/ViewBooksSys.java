package honda.bookworm.SystemTests.TestClasses;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
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
public class ViewBooksSys {
    @Rule
    public ActivityScenarioRule<Home_ViewHandler> activityRule = new ActivityScenarioRule<>(Home_ViewHandler.class);

    @Test
    public void viewBook() throws InterruptedException {
        //check if user can see genres section of the home page
        onView(withText("Fiction")).check(matches(isDisplayed()));

        //check if any book exist on the home page
        onView(firstView(withId(R.id.books_horizontal_recyclerview))).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        pressBack();

        Thread.sleep(2000);

        //test scrolling to bottom of home page
        onView(withId(R.id.home_scroll_view)).perform(swipeUp());
    }
}
