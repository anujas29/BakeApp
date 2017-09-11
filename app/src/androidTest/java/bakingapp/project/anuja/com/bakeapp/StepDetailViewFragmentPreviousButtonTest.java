package bakingapp.project.anuja.com.bakeapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;

/**
 * Created by USER on 18-08-2017.
 */

@RunWith(AndroidJUnit4.class)
public class StepDetailViewFragmentPreviousButtonTest {

    public static final String DESCRIPTION = "3. Mix both sugars into the melted chocolate in a large mixing bowl until mixture is smooth and uniform.";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void clickPreviousButton_ChangesDescription() {
        try{
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.activity_item_list)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));

        try{
            sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on previous button
        onView((withId(R.id.button_prev)))
                .perform(click());

        // Verify that the decrement button decreases the quantity by 1
        onView(withId(R.id.description)).check(matches(withText(DESCRIPTION)));
    }
}
