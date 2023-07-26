package honda.bookworm.SystemTests.TestClasses;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

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
public class OpenProfileSys {
    @Rule
    public ActivityScenarioRule<Home_ViewHandler> activityRule = new ActivityScenarioRule<>(Home_ViewHandler.class);

    @Test
    public void favGenre(){
        //click user profile button
        onView(withId(R.id.home_userProfile_button)).perform(click());

        //fill information of existing user
        onView(withId(R.id.userlogin_username_input)).perform(typeText("notAuthor"));
        onView(withId(R.id.userlogin_password_input)).perform(typeText("abc123"));

        //click login button
        onView(withId(R.id.userlogin_login_button)).perform(click());

        //go to user profile
        onView(withId(R.id.home_userProfile_button)).perform(click());
        onView(withId(R.id.home_userProfile_viewProfile)).perform(click());

        //click the edit toggle button
        onView(withId(R.id.userprofile_edit_toggle)).perform(click());

        //select genre from the list
        onView(withId(R.id.userfavgenre_genre_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());

        //click the + button
        onView(withId(R.id.userfavgenre_add_genre)).perform(click());

        //check if the Fiction genre is there
        onView(withId(R.id.fav_genre_pill_text)).check(matches(withText("Fiction")));

        //remove the fiction button
        onView(withId(R.id.fav_genre_pill_remove)).perform(click());

        //click the check button
        onView(withId(R.id.userprofile_edit_toggle)).perform(click());

        //logout
        pressBack();
        onView(withId(R.id.home_userProfile_button)).perform(click());
        onView(withId(R.id.home_userProfile_logoutButton)).perform(click());
    }
}
