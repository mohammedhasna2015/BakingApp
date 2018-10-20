package com.mohamedcoder.baking1016;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.mohamedcoder.baking1016.activities.DetailActivity;
import com.mohamedcoder.baking1016.activities.MainActivity;
import com.mohamedcoder.baking1016.activities.VideoActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by Mohamed AbuHasna  on 18/10/18.
 */
@RunWith(AndroidJUnit4.class)
public class DetailActivityIntentTest {

    private static final String RECIPE_ITEM_BROWNIE = "Brownies";

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(
            MainActivity.class);
    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResources() {
        mIdlingResource = mActivityRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }


    @Test
    public void clickRecipe_LaunchDetailActivityIntent() {
        onView(ViewMatchers.withId(com.developers.bakingapp.R.id.recipe_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(RECIPE_ITEM_BROWNIE)), click()));
        Context targetContext = InstrumentationRegistry.getTargetContext();
        targetContext.getResources().getBoolean(com.developers.bakingapp.R.bool.tab);
        Boolean isTabletUsed = targetContext.getResources().getBoolean(com.developers.bakingapp.R.bool.tab);
        if (!isTabletUsed) {
            //if tablet is not used this test ensures that detailActivityOpens
            intended(hasComponent(DetailActivity.class.getName()));
        }

        if (isTabletUsed) {
            //To ensure that video fragment is present and master flow is correctly implemented
            onView(ViewMatchers.withId(com.developers.bakingapp.R.id.video_container_tab)).check(matches(isDisplayed()));
        }

    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

}
