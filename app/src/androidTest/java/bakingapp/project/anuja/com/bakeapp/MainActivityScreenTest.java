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

/**
 * Created by USER on 17-08-2017.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    public static final String RECIPE_NAME = "Brownies";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecycleViewItem_OpensRecipeActivity() {

        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.textview_recipe)).check(matches(withText(RECIPE_NAME)));

    }

}
