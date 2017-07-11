package com.acme.a3csci3130;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.widget.ListView;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Created by Dave on 7/11/2017.
 * Test for Creation of a business contact
 */

public class CreateContactActivityTests {

    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testCreateContact() throws Exception{

        // Make strings for testing
        String name = "Clearwater Seafood";
        String address = "123 Test Court";
        String number = "123456789";
        String province = "NS";
        String primaryBusiness = "Fish Monger";


        // submit it
        onView(withId(R.id.submitButton)).perform(click());
        SystemClock.sleep(1500);


        // Input the test strings
        onView(withId(R.id.name)).perform(typeText(name)).perform(closeSoftKeyboard());
        onView(withId(R.id.address)).perform(typeText(address)).perform(closeSoftKeyboard());
        onView(withId(R.id.number)).perform(typeText(number)).perform(closeSoftKeyboard());
        onView(withId(R.id.province)).perform(typeText(province)).perform(closeSoftKeyboard());
        onView(withId(R.id.type)).perform(typeText(primaryBusiness)).perform(closeSoftKeyboard());


        ListView listView = (ListView) mActivityRule.getActivity().findViewById(R.id.listView);

        // Count the number of elements in the DB
        int count = listView.getCount();
        onView(withId(R.id.submitButton)).perform(click());

        //
        SystemClock.sleep(1500);

        // Check the count
        assertEquals(listView.getCount(), count + 1);
    }
}
