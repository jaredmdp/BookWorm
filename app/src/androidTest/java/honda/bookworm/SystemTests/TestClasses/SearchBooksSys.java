package honda.bookworm.SystemTests.TestClasses;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;

import android.view.KeyEvent;

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
public class SearchBooksSys {
    @Rule
    public ActivityScenarioRule<Home_ViewHandler> activityRule = new ActivityScenarioRule<>(Home_ViewHandler.class);

    @Test
    public void searchBooksGenre(){
        //click search logo button
        onView(withId(R.id.home_proceed_to_search)).perform(scrollTo(), click());

        //search by genre
        onView(withId(R.id.search_search_input)).perform(typeText("Fiction"));
        onView(withId(R.id.search_search_input)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

        //check if any book exist
        onView(withId(R.id.search_book_recycler)).check(matches(isDisplayed()));

        //click the 1st book
        onView(withId(R.id.search_book_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        //check if book is really Fiction
        onView(withId(R.id.book_view_book_genre)).check(matches(withText("Genre:  Fiction")));
    }

    @Test
    public void searchBookAuthor(){
        //click search logo button
        onView(withId(R.id.home_proceed_to_search)).perform(scrollTo(), click());

        //click the author button
        onView(withId(R.id.search_for_author)).perform(click());

        //search by author
        onView(withId(R.id.search_search_input)).perform(typeText("George"));
        onView(withId(R.id.search_search_input)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

        //check if any book exist
        onView(withId(R.id.search_book_recycler)).check(matches(isDisplayed()));

        //click the 1st book
        onView(withId(R.id.search_book_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));

        //check if book author is true
        onView(withId(R.id.book_view_book_author)).check(matches(withText(containsString("Author:  George"))));
    }

    @Test
    public void searchBookTitle() throws InterruptedException {
        //click search logo button
        onView(withId(R.id.home_proceed_to_search)).perform(scrollTo(), click());

        //slow the app down. sometimes the test run too fast and unable to scroll
        Thread.sleep(2000);

        //scroll
        onView(withId(R.id.search_horizontal_scroll)).perform(swipeLeft());

        Thread.sleep(2000);

        //click the title button
        onView(withId(R.id.search_for_book_title)).perform(click());

        //search by title
        onView(withId(R.id.search_search_input)).perform(typeText("Neuromancer"));
        onView(withId(R.id.search_search_input)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

        //check if any book exist
        onView(withId(R.id.search_book_recycler)).check(matches(isDisplayed()));

        //click the 1st book
        onView(withId(R.id.search_book_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        //check if book title is true
        onView(withId(R.id.book_view_book_title)).check(matches(withText(containsString("Title:  Neuromancer"))));
    }

    @Test
    public void searchBookISBN() throws InterruptedException {
        //click search logo button
        onView(withId(R.id.home_proceed_to_search)).perform(scrollTo(), click());

        //slow the app down. sometimes the test run too fast and unable to scroll
        Thread.sleep(2000);

        //scroll
        onView(withId(R.id.search_horizontal_scroll)).perform(swipeLeft());

        Thread.sleep(2000);

        //click the ISBN button
        onView(withId(R.id.search_for_isbn)).perform(click());

        //search by ISBN
        onView(withId(R.id.search_search_input)).perform(typeText("9780451457998"));
        onView(withId(R.id.search_search_input)).perform(pressKey(KeyEvent.KEYCODE_ENTER));

        //check if any book exist
        onView(withId(R.id.search_book_recycler)).check(matches(isDisplayed()));

        //click the 1st book
        onView(withId(R.id.search_book_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        //check if book ISBN is true
        onView(withId(R.id.book_view_book_isbn)).check(matches(withText("ISBN:  9780451457998")));
    }



}
