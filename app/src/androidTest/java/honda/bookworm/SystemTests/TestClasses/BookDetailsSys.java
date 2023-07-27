package honda.bookworm.SystemTests.TestClasses;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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
public class BookDetailsSys {
    @Rule
    public ActivityScenarioRule<Home_ViewHandler> activityRule = new ActivityScenarioRule<>(Home_ViewHandler.class);

    @Test
    public void bookDetails(){
        //go to a specific book details page
        onView(withText("Neuromancer")).perform(scrollTo(),click());

        //check if each book detail matched the desired book
        onView(withId(R.id.book_view_book_title)).check(matches(withText("Title:  Neuromancer")));
        onView(withId(R.id.book_view_book_author)).check(matches(withText("Author:  George Orwell")));
        onView(withId(R.id.book_view_book_genre)).check(matches(withText("Genre:  Sci-Fi")));
        onView(withId(R.id.book_view_book_isbn)).check(matches(withText("ISBN:  9780451457998")));
        onView(withId(R.id.book_view_book_description)).check(matches(withText("Description:  A classic cyberpunk novel")));

    }
}
