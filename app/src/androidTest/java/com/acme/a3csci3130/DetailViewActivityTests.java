package com.acme.a3csci3130;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;
import android.widget.ListView;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.contains;

import android.view.View;


import org.junit.Rule;

/**
 * Created by Dave on 7/11/2017.
 */

public class DetailViewActivityTests {

    // Begin the MainActivity
    @Rule
    public final ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    /**
     * Reuse method from CreateContactActivityTests to make test object
     *
     * @throws Exception
     */
    public void createContact() {

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

    }

    /**
     * Test Delete function by creating a business contact and check the count to make sure it's deleted
     * */
    @Test
    public void testContactDelete() throws Exception{

        int count;

        // Get the list
        ListView listView = (ListView) mActivityRule.getActivity().findViewById(R.id.listView);

        // Create our test business contact
        createContact();

        // Get count before testing Delete
        count = listView.getCount();

        // Remove the last entry from the list
        onData(anything()).inAdapterView(allOf(withId(R.id.listView), isCompletelyDisplayed())).atPosition(listView.getCount()-1).perform(click());
        onView(withId(R.id.deleteButton)).perform(click());

        // check if the count has been decreased by 1
        assertEquals(listView.getCount(), count - 1);
    }

    /**
     * Test Update function by creating a contact and altering details
     * */
    @Test
    public void testUpdate() throws Exception {

        // Create new business number to test with
        String newNumber = "987654321";

        // Get the list
        ListView listView = (ListView) mActivityRule.getActivity().findViewById(R.id.listView);

        // Create our test business contact
        createContact();

        // Get the index of our test business contact
        int index = listView.getCount() - 1;
        onData(anything()).inAdapterView(allOf(withId(R.id.listView), isCompletelyDisplayed())).atPosition(index).perform(click());

        // Change the number field to match our test value
        onView(withId(R.id.readnumber)).perform(replaceText(newNumber)).perform(closeSoftKeyboard());

        // Submit
        onView(withId(R.id.updateButton)).perform(click());

        // Wait in case of any race conditions
        SystemClock.sleep(1500);

        // Check on contact again using index
        onData(anything()).inAdapterView(allOf(withId(R.id.listView), isCompletelyDisplayed())).atPosition(index).perform(click());

        // Check if data has changed
        onView(allOf(withId(R.id.readnumber), withText(newNumber)));
    }
}
