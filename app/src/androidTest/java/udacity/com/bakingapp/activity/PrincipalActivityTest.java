package udacity.com.bakingapp.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import udacity.com.bakingapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PrincipalActivityTest {

    @Rule
    public ActivityTestRule<PrincipalActivity> mActivityTestRule = new ActivityTestRule<>(PrincipalActivity.class);

    @Test
    public void principalActivityTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipes_recycler), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction cardView = onView(
                allOf(withId(R.id.ingredients_card), isDisplayed()));
        cardView.perform(click());

        pressBack();

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.steps_recycler), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.next_button), withText("Próximo"), isDisplayed()));
        appCompatButton.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.next_button), withText("Próximo"), isDisplayed()));
        appCompatButton2.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.prev_button), withText("Anterior"), isDisplayed()));
        appCompatButton3.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.prev_button), withText("Anterior"), isDisplayed()));
        appCompatButton4.perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.prev_button), withText("Anterior"), isDisplayed()));
        appCompatButton5.perform(click());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();
    }

}
