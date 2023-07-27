package honda.bookworm.SystemTests.TestClasses;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.honda.bookworm.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import honda.bookworm.Application.Services;
import honda.bookworm.Business.Exceptions.Users.UserNotFoundException;
import honda.bookworm.Data.IUserPersistence;
import honda.bookworm.Object.User;
import honda.bookworm.View.Home_ViewHandler;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateProfileSys {
    @Rule
    public ActivityScenarioRule<Home_ViewHandler> activityRule = new ActivityScenarioRule<>(Home_ViewHandler.class);
    private IUserPersistence userPersistence;
    @Before
    public void setup() {
        userPersistence = Services.getUserPersistence();
        removeUser();
    }


    @Test
    public void signUp(){
        User user = new User("new","author","newAuthor","abc123");
        //click the user profile button on home page
        onView(withId(R.id.home_userProfile_button)).perform(click());

        //click not a user button
        onView(withId(R.id.userlogin_signup_link)).perform(click());

        //fill information for signing up
        onView(withId(R.id.signup_firstname_input)).perform(typeText(user.getFirstName()));
        closeSoftKeyboard();

        onView(withId(R.id.signup_lastname_input)).perform(typeText(user.getLastName()));
        closeSoftKeyboard();

        onView(withId(R.id.signup_username_input)).perform(typeText(user.getUsername()));
        closeSoftKeyboard();

        onView(withId(R.id.signup_password_input)).perform(typeText(user.getPassword()));
        closeSoftKeyboard();

        //click the sign up button
        onView(withId(R.id.signup_signup_button)).perform(click());

        //logout
        onView(withId(R.id.home_userProfile_button)).perform(click());
        onView(withId(R.id.home_userProfile_logoutButton)).perform(click());
    }

    @Test
    public void login(){
        //login
        onView(withId(R.id.home_userProfile_button)).perform(click());

        //fill username and password
        onView(withId(R.id.userlogin_username_input)).perform(typeText("rowling"));
        onView(withId(R.id.userlogin_password_input)).perform(typeText("harrypotter"));
        closeSoftKeyboard();

        //click login button
        onView(withId(R.id.userlogin_login_button)).perform(click());

        //go to user profile
        onView(withId(R.id.home_userProfile_button)).perform(click());
        onView(withId(R.id.home_userProfile_viewProfile)).perform(click());

        //check if the full name and username are correct
        onView(withId(R.id.userprofile_user_fullname)).check(matches(withText("J.K. Rowling")));
        onView(withId(R.id.userprofile_user_username)).check(matches(withText("@rowling")));

        //logout
        pressBack();
        onView(withId(R.id.home_userProfile_logoutButton)).perform(click());
    }

    private void removeUser(){
        try {
            userPersistence.getUserByUsername("newAuthor");
            userPersistence.removeUser("newAuthor");
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
